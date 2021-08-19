package com.lsm.shopping.cart.resp;


import java.math.BigDecimal;

public class ShoppingCartBean {
    private Integer shoppingCartId;
    private Integer productId;
    private Integer merchantId;
    private Integer unit;
    private String merchantName;
    private String productName;
    private BigDecimal price;

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public ShoppingCartBean setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public ShoppingCartBean setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public ShoppingCartBean setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public Integer getUnit() {
        return unit;
    }

    public ShoppingCartBean setUnit(Integer unit) {
        this.unit = unit;
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public ShoppingCartBean setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ShoppingCartBean setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ShoppingCartBean setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "ShoppingCartResp{" +
                "shoppingCartId=" + shoppingCartId +
                ", productId=" + productId +
                ", merchantId=" + merchantId +
                ", unit=" + unit +
                ", merchantName='" + merchantName + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }
}
