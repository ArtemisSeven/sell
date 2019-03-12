package cyx.sell.service;

import cyx.sell.dto.OrderDTO;

public interface PushMessageService {
    void orderStatus(OrderDTO orderDTO);
}
