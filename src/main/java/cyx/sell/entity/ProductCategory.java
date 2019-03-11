package cyx.sell.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;//注意是util包里的Date对应sql里的timestamp

@Entity
@Data
@DynamicUpdate//加了这个注解数据库的update_time字段的on update current_timestamp才会生效
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//如果不指定strategy在测试saveTest()时会报错Table 'sell.hibernate_sequence' doesn't exist
    private int categoryId;
    private String categoryName;
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

}
