CREATE TABLE `t_user`
(
    `id`     int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`   varchar(20) NOT NULL COMMENT '姓名',
    `mobile` varchar(20) NOT NULL COMMENT '手机号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

INSERT INTO t_user(name, mobile)
VALUES ('admin', '17727921123');

CREATE TABLE `t_merchant`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` varchar(20) NOT NULL COMMENT '商家名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商家表';

INSERT INTO t_merchant(name)
VALUES ('魔法商店');
INSERT INTO t_merchant(name)
VALUES ('神奇商店');

CREATE TABLE `t_product`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name`        varchar(20)    NOT NULL COMMENT '商品名称',
    `price`       decimal(12, 2) NOT NULL COMMENT '商品价格',
    `stock`       int(5) NOT NULL COMMENT '库存数量',
    `merchant_id` int(11) NOT NULL COMMENT '商家ID',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品表';

INSERT INTO t_product(name, price, stock, merchant_id)
VALUES ('iphone30', 1, 100, 1);
INSERT INTO t_product(name, price, stock, merchant_id)
VALUES ('ipad', 1, 100, 2);
INSERT INTO t_product(name, price, stock, merchant_id)
VALUES ('macbookpro', 1, 100, 2);

CREATE TABLE `t_shopping_cart`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`     int(11) NOT NULL COMMENT '用户ID',
    `product_id`  int(11) NOT NULL COMMENT '商品ID',
    `merchant_id` int(11) NOT NULL COMMENT '商家ID',
    `unit`        int(5) NOT NULL COMMENT '购物车商品数量',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='购物车表';

INSERT INTO t_shopping_cart(user_id, product_id, merchant_id, unit)
VALUES (1, 1, 1, 1);
INSERT INTO t_shopping_cart(user_id, product_id, merchant_id, unit)
VALUES (1, 2, 2, 1);
INSERT INTO t_shopping_cart(user_id, product_id, merchant_id, unit)
VALUES (1, 3, 2, 1);
