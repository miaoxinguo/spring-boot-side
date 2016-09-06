package jedis;


import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;


/**
 * test jedis
 *
 * Created by miaoxinguo on 2016/9/5.
 */
public class TestJedis {

    private Jedis jedis;
    private Jedis otherClient;

    @Before
    public void init() {
        jedis = new Jedis("121.42.58.92");
        jedis.auth("isiagvK6TQqCt84");

        otherClient = new Jedis("121.42.58.92");
        otherClient.auth("isiagvK6TQqCt84");
    }

    /**
     * 测试基本事务
     */
    @Test
    public void testBaseTransaction() {
        Transaction tx = jedis.multi();
        tx.set("test_aaa", "20");
        tx.incr("test_aaa");

        List<Object> list = tx.exec();
        System.out.println(list);
    }

    /**
     * 测试事务中某一命令错误
     */
    @Test
    public void testTransactionError() {
        Transaction tx = jedis.multi();

        tx.set("test_aaa", "20");
        tx.incr("test_aaa");

        tx.set("test_bbb", "bbb");
        tx.incr("test_bbb");

        tx.set("test_ccc", "10");
        tx.incr("test_ccc");

        List<Object> list = tx.exec();
        System.out.println(list);
    }

    @Test
    public void testTransactionWatch() {
        jedis.set("test_tx", "1");
        jedis.watch("test_tx");

        otherClient.set("test_tx", "2");  // key=test_tx 被改变

        jedis.unwatch();  // 取消监视

        Transaction tx = jedis.multi();
        tx.incr("test_tx");
        List<Object> list = tx.exec();

        System.out.println(list);  // 结果： [3]

        System.out.println(jedis.get("test_tx"));  // 结果： 3
    }

    @Test
    public void testTransactionDiscard() {
        Transaction tx = jedis.multi();
        tx.set("test_tx", "1");
        tx.discard();
        tx.exec();  // 结果： JedisDataException: ERR EXEC without MULTI
    }

    @Test
    public void testGetString() {
        System.out.println(jedis.get("*"));
    }
}
