package cyx.sell.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * http请求最外层数据的结构
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -6929075006970358121L;
    private int code;
    private String msg;
    private T data;
}

