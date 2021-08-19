package com.lsm.shopping.cart.controller;

import com.lsm.shopping.cart.dto.ProductDTO;
import com.lsm.shopping.cart.resp.ShoppingCartResp;
import com.lsm.shopping.cart.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/shopping/cart")
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;

    /**
     * mysql查看购物车
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/allMysql", method = RequestMethod.GET)
    public List<ShoppingCartResp> allMysql(@RequestHeader(value = "userId") Integer userId) {
        return shoppingCartService.allMysql(userId);
    }

    /**
     * 添加商品到购物车
     *
     * @param userId
     * @param productDTO
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(@RequestHeader(value = "userId") Integer userId, @RequestBody ProductDTO productDTO) {
        shoppingCartService.add(userId, productDTO);
    }

    /**
     * 从购物车中移除商品
     *
     * @param userId
     * @param productDTO
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove(@RequestHeader(value = "userId") Integer userId, @RequestBody ProductDTO productDTO) {
        shoppingCartService.remove(userId, productDTO);
    }

    /**
     * 购物车商品数量加减1
     *
     * @param userId
     * @param productDTO
     */
    @RequestMapping(value = "/unitChange", method = RequestMethod.POST)
    public void unitChange(@RequestHeader(value = "userId") Integer userId, @RequestBody ProductDTO productDTO) {
        shoppingCartService.unitChange(userId, productDTO);
    }

    /**
     * redis查看购物车
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/allRedis", method = RequestMethod.GET)
    public List<ShoppingCartResp> allRedis(@RequestHeader(value = "userId") Integer userId) {
        return shoppingCartService.allRedis(userId);
    }
}
