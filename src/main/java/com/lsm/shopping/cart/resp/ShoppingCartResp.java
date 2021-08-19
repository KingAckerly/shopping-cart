package com.lsm.shopping.cart.resp;

import java.util.List;

public class ShoppingCartResp {
    private Integer merchantId;
    private String merchantName;
    private List<ShoppingCartInfoResp> shoppingCartInfoResps;

    public Integer getMerchantId() {
        return merchantId;
    }

    public ShoppingCartResp setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public ShoppingCartResp setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public List<ShoppingCartInfoResp> getShoppingCartInfoResps() {
        return shoppingCartInfoResps;
    }

    public ShoppingCartResp setShoppingCartInfoResps(List<ShoppingCartInfoResp> shoppingCartInfoResps) {
        this.shoppingCartInfoResps = shoppingCartInfoResps;
        return this;
    }

    @Override
    public String toString() {
        return "ShoppingCartResp{" +
                "merchantId=" + merchantId +
                ", merchantName='" + merchantName + '\'' +
                ", shoppingCartInfoResps=" + shoppingCartInfoResps +
                '}';
    }
}
