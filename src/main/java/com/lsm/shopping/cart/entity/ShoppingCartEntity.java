package com.lsm.shopping.cart.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_shopping_cart")
public class ShoppingCartEntity implements Serializable {

    private static final long serialVersionUID = -1209223672360334786L;

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer merchantId;
    private Integer unit;

    public Integer getId() {
        return id;
    }

    public ShoppingCartEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public ShoppingCartEntity setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public ShoppingCartEntity setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public ShoppingCartEntity setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public Integer getUnit() {
        return unit;
    }

    public ShoppingCartEntity setUnit(Integer unit) {
        this.unit = unit;
        return this;
    }

    @Override
    public String toString() {
        return "ShoppingCartEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", merchantId=" + merchantId +
                ", unit=" + unit +
                '}';
    }
}
