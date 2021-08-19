package com.lsm.shopping.cart.resp;

import java.math.BigDecimal;

public class ShoppingCartInfoResp {
    private Integer shoppingCartId;
    private Integer productId;
    private String productName;
    private BigDecimal price;
    private Integer unit;

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public ShoppingCartInfoResp setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public ShoppingCartInfoResp setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ShoppingCartInfoResp setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ShoppingCartInfoResp setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getUnit() {
        return unit;
    }

    public ShoppingCartInfoResp setUnit(Integer unit) {
        this.unit = unit;
        return this;
    }

    @Override
    public String toString() {
        return "ShoppingCartInfoResp{" +
                "shoppingCartId=" + shoppingCartId +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", unit=" + unit +
                '}';
    }
}
