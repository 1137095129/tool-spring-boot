if redis.call('get', KEYS[1]) == ARGS[1] then
    if redis.call('expire',KEYS[1],ARGS[2]) then
        return true
    end
end
return false