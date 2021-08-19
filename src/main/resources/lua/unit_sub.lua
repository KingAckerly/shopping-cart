local unit = redis.call('hget', KEYS[1], KEYS[2])
redis.call('hset', KEYS[1], KEYS[2], unit - 1)
