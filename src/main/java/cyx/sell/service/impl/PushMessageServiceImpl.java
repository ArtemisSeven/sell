package cyx.sell.service.impl;

import cyx.sell.config.WechatAccountConfig;
import cyx.sell.dto.OrderDTO;
import cyx.sell.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    @Autowired
    private WxMpServiceImpl wxMpService;
    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        List<WxMpTemplateData> data= Arrays.asList(new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyword1","麦当劳"),new WxMpTemplateData("keyword2","10086"),
                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMessage()),
                new WxMpTemplateData("remark","记得五星好评哦"));

        WxMpTemplateMessage wxMpTemplateMessage=new WxMpTemplateMessage();
        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());
        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get("order-status"));
        wxMpTemplateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("【微信消息模版】推送失败,e={}",e.getMessage());//注意异常不要继续往上抛(会导致上层事务回滚)，消息推送失败也没关系，所以直接处理异常即可
        }

    }
}
