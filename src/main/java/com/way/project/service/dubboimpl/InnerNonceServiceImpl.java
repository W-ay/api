package com.way.project.service.dubboimpl;

import cn.hutool.core.util.RandomUtil;
import com.way.dubbointerface.service.InnerNonceService;
import com.way.project.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 实现nonce service
 *
 * @author Way
 */
@DubboService
@Slf4j
public class InnerNonceServiceImpl implements InnerNonceService {
    @Resource
    private RedisUtils redisUtils;
    private final String TABLE_NAME = "name_nonce";
    @Override
    public String getNonce() {
        ZSetOperations ops = redisUtils.getStringRedisTemplate().opsForZSet();
        String value;
        //判断是否存在
        int count = 0;
        while (true) {
            count++;
            value = RandomUtil.randomNumbers(6);
            Double score = ops.score(TABLE_NAME, value);
            if (score == null) {
                break;
            }
            if (count >= 10){
                log.error("随机数重复超过10次");
                throw new RuntimeException("随机数重复超过10次");
            }
        }
        //添加元素
        ops.add(TABLE_NAME, value, 0);
        Set range = ops.range(TABLE_NAME, 0, -1);
        return value;
    }

    @Override
    public Boolean removeNonce(String nonce) {
        ZSetOperations ops = redisUtils.getStringRedisTemplate().opsForZSet();
        Double score = ops.score(TABLE_NAME, nonce);
        //todo nonce还需判断时间是否超时
        if (score==null||score!=0){
            return false;
        }
        ops.add(TABLE_NAME,nonce,System.currentTimeMillis());
        return true;
    }
}
