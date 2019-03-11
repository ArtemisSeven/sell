package cyx.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cyx.sell.entity.OrderDetail;
import cyx.sell.enums.PayStatusEnum;
import cyx.sell.enums.OrderStatusEnum;
import cyx.sell.serializer.DateToLongSerializer;
import cyx.sell.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)//如果某个字段为null则不返回此字段,如果要对所有对象进行这种设置，则在application.yml文件里添加spring:jackson:default-property-inclusion: non_null
@Data
public class OrderDTO {
    /** 订单id. */
    @Id
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus ;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using=DateToLongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using=DateToLongSerializer.class)
    private Date updateTime;

    /** 订单详情列表. */
    private List<OrderDetail> orderDetailList=new ArrayList<>();//如果这个字段必须返回，但是又是null，为了避免被JsonInclude过滤掉此字段，需要给该字段初始化为一个空的集合

    @JsonIgnore//加上这个注解之后，如果OrderDTO以Json格式返回，则忽略这两个方法
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
