local score = redis.call('zscore', KEYS[1], KEYS[2])
if score == false or nil == score then
    return
end
if tonumber(ARGV[1]) == 0 then
    redis.call('zincrby', KEYS[1], 1, KEYS[2])
    return
end
if tonumber(ARGV[1]) == 1 then
    redis.call('zincrby', KEYS[1], -1, KEYS[2])
    return
end
