--单点登录并使用redis作为用户token存储时，采用的数据类型为hash
--KEYS[1]每个用户独特唯一的token的key
--KEYS[2]每个用户每次登录时随机生成的token的key
--ARGS[1]为用户请求时携带的token的value
--ARGS[2]为验证token成功时重新设置失效时间的值
if redis.call('hget', KEYS[1], KEYS[2]) == ARGS[1] then
    if redis.call('expire', KEYS[1], ARGS[2]) == 1 then
        return true
    end
end
return false