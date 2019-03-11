package cyx.sell.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cyx.sell.enums.ProductStatusEnum;
import cyx.sell.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
@Proxy(lazy = false)//为了避免报错org.hibernate.LazyInitializationException: could not initialize proxy [cyx.sell.entity.Product#1] - no Session
public class Product {
    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private int productStock;
    private String productDescription;
    private String productIcon;
    private int productStatus=ProductStatusEnum.UP.getCode();
    private int categoryType;
    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
