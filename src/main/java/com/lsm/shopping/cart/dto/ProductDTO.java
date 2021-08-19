package com.lsm.shopping.cart.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private Integer productId;
    private String productName;
    private Integer merchantId;
    private String merchantName;
    private BigDecimal price;
    private String change;

    public Integer getProductId() {
        return productId;
    }

    public ProductDTO setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public ProductDTO setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public ProductDTO setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getChange() {
        return change;
    }

    public ProductDTO setChange(String change) {
        this.change = change;
        return this;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", merchantId=" + merchantId +
                ", merchantName='" + merchantName + '\'' +
                ", price=" + price +
                ", change='" + change + '\'' +
                '}';
    }
}
