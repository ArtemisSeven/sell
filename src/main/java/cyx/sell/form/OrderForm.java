package cyx.sell.form;

import org.hibernate.validator.constraints.NotEmpty;
import sun.plugin2.message.Message;

public class OrderForm {
    @NotEmpty(message="姓名不能为空")
    private String name;
    @NotEmpty(message = "手机号不能为空")
    private String phone;
    @NotEmpty(message = "地址不能为空")
    private String address;
    @NotEmpty(message = "买家微信openid不能为空")
    private String openid;
    @NotEmpty(message = "购物车不能为空")
    private String items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
