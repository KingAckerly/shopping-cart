local user = redis.call('exists', KEYS[1])
if user == false or nil == user then
    redis.call('hset', KEYS[1], KEYS[2], 1)
else
    redis.call('hincrby', KEYS[1], KEYS[2], 1)
end
