package cyx.sell.service.impl;

import cyx.sell.converter.OrderMasterToOrderDTOConverter;
import cyx.sell.dao.OrderDetailDao;
import cyx.sell.dao.OrderMasterDao;
import cyx.sell.dto.CartDTO;
import cyx.sell.dto.OrderDTO;
import cyx.sell.entity.OrderDetail;
import cyx.sell.entity.OrderMaster;
import cyx.sell.enums.PayStatusEnum;
import cyx.sell.entity.Product;
import cyx.sell.enums.OrderStatusEnum;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.service.OrderService;
import cyx.sell.utils.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger log=LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private PayServiceImpl payService;
    @Autowired
    private PushMessageServiceImpl pushMessageService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId=KeyUtil.getUniqueKey();
        BigDecimal orderAmount=new BigDecimal(0);
        //1.查询商品(数量、单价)
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            Product product=productService.findOne(orderDetail.getProductId());
            if (product==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价
            orderAmount=orderAmount.add(product.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
            //3.保存OrderDetail
            BeanUtils.copyProperties(product,orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailDao.save(orderDetail);
        }
        //4.保存OrderMaster
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);
        //5.扣减库存
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
//        OrderMaster orderMaster=orderMasterDao.findById(orderId).get();
        Optional<OrderMaster> optionalT=orderMasterDao.findById(orderId);
        OrderMaster orderMaster=(optionalT.isPresent()?optionalT.get():null);
        if (orderMaster==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList=orderDetailDao.findByOrderId(orderId);
        if (orderDetailList==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAllByOpenid(String openid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterDao.findByBuyerOpenid(openid,pageable);
        List<OrderDTO> orderDTOList=OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage=new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中无商品详情, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已支付, 需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //推送微信模版消息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null) {
            log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findAll(Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList=OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage=new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }
}
