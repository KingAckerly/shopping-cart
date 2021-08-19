package com.lsm.shopping.cart.service;

import com.lsm.shopping.cart.dto.ProductDTO;
import com.lsm.shopping.cart.resp.ShoppingCartResp;

import java.util.List;

public interface IShoppingCartService {
    List<ShoppingCartResp> allMysql(Integer userId);

    void add(Integer userId, ProductDTO productDTO);

    void remove(Integer userId, ProductDTO productDTO);

    void unitChange(Integer userId, ProductDTO productDTO);

    List<ShoppingCartResp> allRedis(Integer userId);
}
