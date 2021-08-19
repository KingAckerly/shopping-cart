package com.lsm.shopping.cart.service.impl;

import com.lsm.shopping.cart.config.LuaScriptConfig;
import com.lsm.shopping.cart.dto.ProductDTO;
import com.lsm.shopping.cart.mapper.IProductMapper;
import com.lsm.shopping.cart.mapper.IShoppingCartMapper;
import com.lsm.shopping.cart.resp.ShoppingCartBean;
import com.lsm.shopping.cart.resp.ShoppingCartInfoResp;
import com.lsm.shopping.cart.resp.ShoppingCartResp;
import com.lsm.shopping.cart.service.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    @Autowired
    private LuaScriptConfig luaScriptConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IShoppingCartMapper shoppingCartMapper;

    @Autowired
    private IProductMapper productMapper;

    @Override
    public List<ShoppingCartResp> allMysql(Integer userId) {
        List<ShoppingCartBean> shoppingCartBeanList = shoppingCartMapper.listShoppingCart(userId);
        List<ShoppingCartResp> shoppingCartResps = build(shoppingCartBeanList);
        return shoppingCartResps;
    }

    @Override
    public void add(Integer userId, ProductDTO productDTO) {
        //判断该用户购物车中是否有该商品
        //如果有则+1,如果没有则set一个新的
        List<String> keys = new ArrayList<>();
        keys.add("user.id:" + userId);
        keys.add("product.id:" + productDTO.getProductId());
        redisTemplate.execute(luaScriptConfig.addProduct, keys, 0);
        //添加商品到hash
        //判断该商品在redis中是否存在,不存在的情况下才添加
        if (!redisTemplate.hasKey("product.id:" + productDTO.getProductId())) {
            redisTemplate.opsForHash().put("product.id:" + productDTO.getProductId(), "merchant.id", productDTO.getMerchantId());
            redisTemplate.opsForHash().put("product.id:" + productDTO.getProductId(), "merchant.name", productDTO.getMerchantName());
            redisTemplate.opsForHash().put("product.id:" + productDTO.getProductId(), "product.name", productDTO.getProductName());
            redisTemplate.opsForHash().put("product.id:" + productDTO.getProductId(), "product.price", productDTO.getPrice());
        }
    }

    @Override
    public void remove(Integer userId, ProductDTO productDTO) {
        redisTemplate.opsForHash().delete("user.id:" + userId, "product.id:" + productDTO.getProductId());
    }

    @Override
    public void unitChange(Integer userId, ProductDTO productDTO) {
        if (productDTO.getChange().equals("add")) {
            redisTemplate.opsForHash().increment("user.id:" + userId, "product.id:" + productDTO.getProductId(), 1);
        }
        if (productDTO.getChange().equals("sub")) {
            List<String> keys = new ArrayList<>();
            keys.add("user.id:" + userId);
            keys.add("product.id:" + productDTO.getProductId());
            redisTemplate.execute(luaScriptConfig.unitSub, keys, 0);
        }
    }

    @Override
    public List<ShoppingCartResp> allRedis(Integer userId) {
        Set<String> fields = redisTemplate.opsForHash().keys("user.id:" + userId);
        if (CollectionUtils.isEmpty(fields)) {
            //该用户购物车是空的
            return null;
        }
        Integer productId;
        ShoppingCartBean shoppingCartBean;
        List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
        for (String field : fields) {
            productId = Integer.valueOf(field.substring(field.indexOf(":") + 1));
            shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setProductId(productId);
            long start = System.currentTimeMillis();
            shoppingCartBean.setProductName((String) redisTemplate.opsForHash().get("product.id:" + productId, "product.name"));
            shoppingCartBean.setPrice((BigDecimal) redisTemplate.opsForHash().get("product.id:" + productId, "product.price"));
            shoppingCartBean.setUnit(Integer.valueOf(redisTemplate.opsForHash().get("user.id:" + userId, "product.id:" + productId).toString()));
            shoppingCartBean.setMerchantId((Integer) redisTemplate.opsForHash().get("product.id:" + productId, "merchant.id"));
            shoppingCartBean.setMerchantName((String) redisTemplate.opsForHash().get("product.id:" + productId, "merchant.name"));
            long end = System.currentTimeMillis();
            System.err.println("耗时:" + (end - start) + "ms");
            shoppingCartBeanList.add(shoppingCartBean);
        }
        List<ShoppingCartResp> shoppingCartResps = build(shoppingCartBeanList);
        return shoppingCartResps;
    }

    /**
     * 构造返回给前端的购物车列表结构
     *
     * @param shoppingCartBeanList
     * @return
     */
    private List<ShoppingCartResp> build(List<ShoppingCartBean> shoppingCartBeanList) {
        Map<Integer, List<ShoppingCartBean>> map = shoppingCartBeanList.stream().collect(Collectors.groupingBy(ShoppingCartBean::getMerchantId));
        ShoppingCartResp shoppingCartResp;
        List<ShoppingCartResp> shoppingCartResps = new ArrayList<>();
        ShoppingCartInfoResp shoppingCartInfoResp;
        List<ShoppingCartInfoResp> shoppingCartInfoResps;
        for (Map.Entry<Integer, List<ShoppingCartBean>> item : map.entrySet()) {
            shoppingCartResp = new ShoppingCartResp();
            shoppingCartResp.setMerchantId(item.getKey());
            shoppingCartResp.setMerchantName(item.getValue().get(0).getMerchantName());
            shoppingCartInfoResps = new ArrayList<>();
            for (ShoppingCartBean bean : item.getValue()) {
                shoppingCartInfoResp = new ShoppingCartInfoResp();
                shoppingCartInfoResp.setShoppingCartId(bean.getShoppingCartId());
                shoppingCartInfoResp.setProductId(bean.getProductId());
                shoppingCartInfoResp.setProductName(bean.getProductName());
                shoppingCartInfoResp.setPrice(bean.getPrice());
                shoppingCartInfoResp.setUnit(bean.getUnit());
                shoppingCartInfoResps.add(shoppingCartInfoResp);
            }
            shoppingCartResp.setShoppingCartInfoResps(shoppingCartInfoResps);
            shoppingCartResps.add(shoppingCartResp);
        }
        return shoppingCartResps;
    }
}
