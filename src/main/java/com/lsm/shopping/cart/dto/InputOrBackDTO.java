package com.lsm.shopping.cart.dto;

public class InputOrBackDTO {
    private Integer productId;
    private Integer inputOrBack;

    public Integer getProductId() {
        return productId;
    }

    public InputOrBackDTO setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getInputOrBack() {
        return inputOrBack;
    }

    public InputOrBackDTO setInputOrBack(Integer inputOrBack) {
        this.inputOrBack = inputOrBack;
        return this;
    }

    @Override
    public String toString() {
        return "InputOrBackDTO{" +
                "productId=" + productId +
                ", inputOrBack=" + inputOrBack +
                '}';
    }
}
