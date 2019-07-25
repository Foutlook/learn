package redisson.lock;

import com.foutin.utils.BaseTest;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * redisson lock测试
 *
 * @author xingkai.fan
 * @create 2019-07-25
 */
public class RedissonDemoTest extends BaseTest {

    @Autowired
    private RedissonClient redissonClient;

    private final static int waitTime = 5;
    private final static int leaseTime = 10;

    @Test
    public void testRedisson() {

        RLock mylock = redissonClient.getLock("mylock");
        try {

            boolean tryLock = mylock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (tryLock) {
                // 成功获取锁 这里处理业务
                System.out.println("fanxignkai llllll");

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            mylock.unlock();
        }
    }

}
