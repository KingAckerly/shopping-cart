package com.lsm.shopping.cart.mapper;

import com.lsm.shopping.cart.resp.ShoppingCartBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShoppingCartMapper {
    List<ShoppingCartBean> listShoppingCart(@Param("userId") Integer userId);
}
