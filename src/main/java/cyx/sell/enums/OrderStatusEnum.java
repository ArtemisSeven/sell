package cyx.sell.enums;

public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISH(1,"已完成"),
    CANCEL(2,"已取消")
    ;
    private Integer code;
    private String message;
    OrderStatusEnum(Integer code,String msg){
        this.code=code;
        this.message=msg;
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
