package cyx.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity

public class Seller {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}
