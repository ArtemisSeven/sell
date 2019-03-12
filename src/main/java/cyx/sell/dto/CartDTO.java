package cyx.sell.dto;

import lombok.Data;

@Data
public class CartDTO {
    private String productId;
    private int productQuantity;

    public CartDTO(String productId, int productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
