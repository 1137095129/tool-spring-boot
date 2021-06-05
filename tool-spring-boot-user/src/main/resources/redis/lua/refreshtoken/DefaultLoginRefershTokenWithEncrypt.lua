if redis.call('get', KEYS[1]) == ARGS[1] then
    if redis.call('expire', KEYS[1], ARGS[3]) == 1 then
        if redis.call('get', KEYS[2]) == ARGS[2] then
            if redis.call('expire', KEYS[2], ARGS[3]) == 1 then
                return true
            else
                redis.call('del',KEYS[1])
            end
        else
            redis.call('del',KEYS[1])
        end
    else
        redis.call('del',KEYS[2])
    end
else
    redis.call('del',KEYS[2])
end
return false