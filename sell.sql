use sell;
show tables;
drop table product;
create table `product`(
	`product_id` varchar(32) not null comment '商品id',-- 企业级商品数量多,int不够用
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '商品单价',
    `product_stock` int not null comment '商品库存',
    `product_description` varchar(64) comment '商品描述',
    `product_icon` varchar(512) comment '商品小图路径',
    `product_status` tinyint(3) default '0' comment '商品状态，0正常1下架',
    `category_type` int not null comment '商品类目',
    `create_time` timestamp not null default current_timestamp,
    `update_time` timestamp not null default current_timestamp on update current_timestamp,
    primary key(`product_id`)
)comment '商品表';
create table `product_category`(
	`category_id` int not null auto_increment,
    `category_name` varchar(64) not null comment '类目名字',
    `category_type` int not null comment '类目编号',
    `create_time` timestamp not null default current_timestamp,
    `update_time` timestamp not null default current_timestamp on update current_timestamp,
    primary key(`category_id`),
    unique key `unq_category_type`(`category_type`)-- 由于商品表引用category_type字段，而且此字段值不能重复，所以建立唯一索引.
)comment '商品类目表';
create table `order_master`(
	`order_id` varchar(32) not null,
    `buyer_name` varchar(32) not null comment '买家名字',
    `buyer_phone` varchar(32) not null comment '买家电话',
    `buyer_address` varchar(128) not null comment '买家地址',
    `buyer_openid` varchar(64) not null comment '买家微信openid',
    `order_amount` decimal(8,2) not null comment '订单总金额',
    `order_status` tinyint(3) not null default '0' comment '订单状态，默认0新下单',
    `pay_status` tinyint(3) not null default '0' comment '支付状态，默认0未支付',
    `create_time` timestamp not null default current_timestamp,
    `update_time` timestamp not null default current_timestamp on update current_timestamp,
    primary key(`order_id`),
    key `idx_buyer_openid` (`buyer_openid`)-- 需要用到openid查询，所以也建立该字段的索引
)comment '订单主表';
create table `order_detail`(
	`detail_id` varchar(32) not null,
	`order_id` varchar(32) not null comment '订单主表id',
	`product_id` varchar(32) not null comment '商品id',-- 企业级商品数量多,int不够用
    `product_name` varchar(64) not null comment '商品名称',
    `product_price` decimal(8,2) not null comment '商品单价',
    `product_quantity` int not null comment '商品数量',
    `product_icon` varchar(512) comment '商品小图路径',
    `create_time` timestamp not null default current_timestamp,
    `update_time` timestamp not null default current_timestamp on update current_timestamp,
    primary key(`detail_id`),
    key `idx_order_id` (`order_id`)-- 需要用到订单主表id查询，所以建立该字段的索引
)comment '订单详情表';
drop table `seller`;
create table `seller`(
	`seller_id` varchar(32) not null,
	`username` varchar(32) not null,
    `password` varchar(32) not null,
    `openid` varchar(64) not null,
    `create_time` timestamp not null default current_timestamp,
    `update_time` timestamp not null default current_timestamp on update current_timestamp,
    primary key(`seller_id`),
    unique key unq_openid (`openid`)
);

