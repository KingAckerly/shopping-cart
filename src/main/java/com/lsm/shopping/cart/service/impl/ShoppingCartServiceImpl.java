package com.lsm.shopping.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lsm.shopping.cart.config.LuaScriptConfig;
import com.lsm.shopping.cart.dto.InputOrBackDTO;
import com.lsm.shopping.cart.dto.ProductDTO;
import com.lsm.shopping.cart.entity.ProductEntity;
import com.lsm.shopping.cart.mapper.IProductMapper;
import com.lsm.shopping.cart.mapper.IShoppingCartMapper;
import com.lsm.shopping.cart.resp.ShoppingCartBean;
import com.lsm.shopping.cart.resp.ShoppingCartInfoResp;
import com.lsm.shopping.cart.resp.ShoppingCartResp;
import com.lsm.shopping.cart.service.IShoppingCartService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
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

    @Autowired
    private RedissonClient redissonClient;

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

    @Override
    public void init() {
        ZSetOperations.TypedTuple<Integer> tuple1 = new DefaultTypedTuple<>(1020, 60.0);
        ZSetOperations.TypedTuple<Integer> tuple2 = new DefaultTypedTuple<>(1000, 70.0);
        ZSetOperations.TypedTuple<Integer> tuple3 = new DefaultTypedTuple<>(1038, 80.0);
        ZSetOperations.TypedTuple<Integer> tuple4 = new DefaultTypedTuple<>(1001, 90.0);
        ZSetOperations.TypedTuple<Integer> tuple5 = new DefaultTypedTuple<>(1005, 100.0);
        redisTemplate.opsForZSet().add("ranking-list", new HashSet<>(Arrays.asList(tuple1, tuple2, tuple3, tuple4, tuple5)));
    }

    @Override
    public void top() {
        //升序rangeWithScores
        //Set<ZSetOperations.TypedTuple<Integer>> tuples = redisTemplate.opsForZSet().rangeWithScores("ranking-list", 0, -1);
        //降序reverseRangeWithScores
        Set<ZSetOperations.TypedTuple<Integer>> tuples = redisTemplate.opsForZSet().reverseRangeWithScores("ranking-list", 0, -1);
        for (ZSetOperations.TypedTuple<Integer> tuple : tuples) {
            System.out.println(tuple.getValue() + " : " + tuple.getScore());
        }
    }

    @Override
    public void inputOrBack(InputOrBackDTO inputOrBackDTO) {
        List<Object> keys = new ArrayList<>();
        keys.add("ranking-list");
        keys.add(inputOrBackDTO.getProductId());
        Integer value = inputOrBackDTO.getInputOrBack();
        redisTemplate.execute(luaScriptConfig.inputBack, keys, value);
    }

    @Override
    public void testRedisson(String productId) {
        String lockKey = "productId:" + productId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            ProductEntity productEntity = productMapper.selectById(productId);
            int stock = productEntity.getStock();
            if (stock <= 0) {
                System.out.println("库存不足");
                return;
            }
            productEntity = new ProductEntity();
            productEntity.setStock(stock - 1);
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("id", productId);
            productMapper.update(productEntity, updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
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
