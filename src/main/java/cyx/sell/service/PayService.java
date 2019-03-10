package cyx.sell.service;

import cyx.sell.dto.OrderDTO;

public interface PayService {
    OrderDTO create(OrderDTO orderDTO);
}
