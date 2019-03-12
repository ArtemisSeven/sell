package cyx.sell.enums;

public enum  ResultEnum {
    SUCCESS(0,"成功"),
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(20,"库存异常"),
    ORDER_NOT_EXIST(30,"订单不存在"),
    ORDER_STATUS_ERROR(40,"订单状态错误"),
    ORDER_UPDATE_FAIL(50,"订单更新失败"),
    ORDER_DETAIL_EMPTY(60,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(70,"订单支付状态不正确"),
    CART_EMPTY(80,"购物车为空"),
    ORDER_OWNER_ERROR(90,"查询的订单不是当前用户"),
    WECHAT_MP_ERROR(100,"微信公众号相关错误"),
    WECHAT_NOTIFY_MONEY_VERIFY_ERROR(110,"微信异步通知金额校验不通过"),
    ORDER_CANCEL_SUCCESS(120,"订单取消成功"),
    ORDER_FINISH_SUCCESS(130,"订单完结成功"),
    PRODUCT_STATUS_ERROR(24, "商品状态不正确"),
    LOGIN_FAIL(140,"登陆失败，登陆信息不正确"),
    LOGOUT_SUCCESS(150,"登出成功")

    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
