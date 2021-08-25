package com.lsm.shopping.cart.controller;

import com.lsm.shopping.cart.dto.InputOrBackDTO;
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

    /**
     * 模拟项目启动,从mysql加载TOP10商品写入redis zset
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init() {
        shoppingCartService.init();
        return "项目启动,从mysql加载TOP10商品写入redis zset";
    }

    /**
     * 模拟每天查一次top10,更新一次redis zset
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update() {
        //TODO
        return "每天查一次TOP10,更新一次redis zset";
    }

    /**
     * 查看商品销量TOP10
     *
     * @return
     */
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String top() {
        shoppingCartService.top();
        return "查看商品销量TOP10";
    }

    /**
     * 模拟下单交易成功,商品销量+1,退货成功,商品销量-1
     *
     * @param inputOrBackDTO
     * @return
     */
    @RequestMapping(value = "/inputOrBack", method = RequestMethod.POST)
    public String inputOrBack(@RequestBody InputOrBackDTO inputOrBackDTO) {
        shoppingCartService.inputOrBack(inputOrBackDTO);
        return "更新商品销量分数成功";
    }

    /**
     * 测试Redisson分布式锁防止商品库存超卖
     *
     * @param productId
     * @return
     */
    @RequestMapping(value = "/testRedisson", method = RequestMethod.POST)
    public String testRedisson(@RequestParam(value = "productId") String productId) {
        shoppingCartService.testRedisson(productId);
        return "测试Redisson分布式锁防止商品库存超卖成功";
    }
}
