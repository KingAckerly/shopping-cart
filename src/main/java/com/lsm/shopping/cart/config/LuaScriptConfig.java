package com.lsm.shopping.cart.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

@Component
public class LuaScriptConfig {

    public final RedisScript<Void> addProduct = getRedisScript("lua/add_product.lua", Void.class);
    public final RedisScript<Void> unitSub = getRedisScript("lua/unit_sub.lua", Void.class);

    private <T> RedisScript<T> getRedisScript(String path, Class<T> resultType) {
        DefaultRedisScript<T> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        redisScript.setResultType(resultType);
        return redisScript;
    }
}
