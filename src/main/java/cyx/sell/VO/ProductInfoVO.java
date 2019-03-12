package cyx.sell.VO;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class ProductInfoVO implements Serializable {
    private static final long serialVersionUID = -8344339584712996957L;
    @JsonProperty("name")//加上注解后返回给前端就是name字段
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductVO> productVOList;
}
