<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <#--订单概要-->
            <div class="col-md-12 column">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>
                            订单id
                        </th>
                        <th>
                            订单总金额
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            ${orderDTO.orderId}
                        </td>
                        <td>
                            ${orderDTO.orderAmount}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        <#--订单详情-->
        <div class="col-md-12 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        商品id
                    </th>
                    <th>
                        商品名称
                    </th>
                    <th>
                        价格
                    </th>
                    <th>
                        数量
                    </th>
                    <th>
                        总额
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTO.getOrderDetailList() as orderDetail>
                <tr>
                    <td>
                        ${orderDetail.getOrderId()}
                    </td>
                    <td>
                        ${orderDetail.getProductName()}
                    </td>
                    <td>
                        ${orderDetail.getProductPrice()}
                    </td>
                    <td>
                        ${orderDetail.getProductQuantity()}
                    </td>
                    <td>
                        ${orderDetail.getProductPrice()*orderDetail.getProductQuantity()}
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#--新订单有两个按钮：完结订单、取消订单-->
        <div class="col-md-4 column">
        <#if orderDTO.getOrderStatusEnum().message == "新订单">
            <a href="/sell/seller/order/finish?orderId=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-primary">完结订单</a>
            <a href="/sell/seller/order/cancel?orderId=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-danger">取消订单</a>
        </#if>
        </div>
    </div>
</div>
</body>
</html>
