package com.lsm.shopping.cart.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("t_product")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = -949015482423889307L;
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Integer merchantId;

    public Integer getId() {
        return id;
    }

    public ProductEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getStock() {
        return stock;
    }

    public ProductEntity setStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public ProductEntity setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", merchantId=" + merchantId +
                '}';
    }
}
