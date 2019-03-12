package cyx.sell.handler;

import cyx.sell.VO.ResultVO;
import cyx.sell.exception.SellException;
import cyx.sell.utils.ResultVOUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SellExceptionHandler {
    /**
     * 这个异常处理类的作用是，创建订单时，如果某个商品时不存在的，返回给前端的json格式仍然是ResultVO，如果不写这个类进行处理，返回给前端的json是非ResultVO格式的
     * @param e
     * @return
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handleSellException(SellException e){
        return ResultVOUtils.error(e.getCode(),e.getMessage());
    }
}
