package jedis;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        jedis.select(13);
        otherClient.select(13);
    }

    @After
    public void destroy() {
        jedis.flushDB();
        otherClient.flushDB();
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

    /**
     * 测试 String 类型
     */
    @Test
    public void testString() {
        // 字符串
        jedis.set("str_key", "value");
        System.out.println(jedis.get("str_key"));  // value

        jedis.append("str_key", "123456");
        System.out.println(jedis.substr("str_key", 1, 16));  // alue123456


        // 数字
        jedis.set("int_key", "123");
        System.out.println(jedis.incr("int_key"));   // 124
        System.out.println(jedis.decrBy("int_key", 4));  // 120

        jedis.set("float_key", "123.23");
        System.out.println(jedis.incrByFloat("float_key", 2)); // 125.23
    }

    /**
     * 测试 List 类型
     */
    @Test
    public void testList() {
        jedis.lpush("list", "a", "b");  // 头头部插 [b, a]
        jedis.rpush("list", "y", "z");  // 从尾部插 [b, a, y, z]

        System.out.println(jedis.lpop("list"));  // 输出： b
        System.out.println(jedis.rpop("list"));  // 输出： z

        jedis.lset("list", 0, "123");  // [123, y]

        System.out.println(jedis.llen("list")); // 输出： 2

        jedis.ltrim("list", 1, 1);
        System.out.println(jedis.lpop("list"));  // 输出： y
    }

    /**
     * 测试 Set 类型
     */
    @Test
    public void testSet() {
        jedis.sadd("set1", "a", "b", "a", "f");
        jedis.sadd("set2", "a", "g");
        jedis.sadd("set3", "b");
        jedis.sadd("set4", "a");

        // 所有元素
        System.out.println(jedis.smembers("set1"));  // 输出： [a, b, f]
        // 长度
        System.out.println(jedis.scard("set1"));  // 输出： 3
        // 是否包含
        System.out.println(jedis.sismember("set1", "a"));  // 输出： true
        System.out.println(jedis.sismember("set1", "z"));  // 输出： false

        // sdiff 从第一个 set 中删除其它 set 中包含的元素
        System.out.println(jedis.sdiff("set1", "set2", "set3"));  // 输出： [f]

        // sdiffstore 和sdiff 一样，不同的是结果存到了一个新的 set
        jedis.sdiffstore("set", "set1", "set2", "set3");
        System.out.println(jedis.smembers("set"));   // 输出： [f]

        // sunion 取并集。sunionstore 同sdiffstore
        System.out.println(jedis.sunion("set1", "set2", "set3"));  // 输出：  [a, b, f, g]

        // sinter 取交集。sinterstore 同上面两个
        System.out.println(jedis.sinter("set1", "set2", "set4"));  // 输出：  [a]
    }


    @Test
    public void testHash() {
        jedis.hset("hash", "key1", "123");

        Map<String, String> data = new HashMap<>();
        data.put("key2", "456");
        data.put("key3", "789");
        jedis.hmset("hash", data);

        System.out.println(jedis.hget("hash", "key1"));  // 123

        Map<String, String> map = jedis.hgetAll("hash");
        System.out.println(map);  // {key1=123, key2=456, key3=789}


    }

    @Test
    public void testZSet() {
        jedis.zadd("z-set", 2.1, "21");
        jedis.zadd("z-set", 2.6, "26");
        jedis.zadd("z-set", 1.5, "15");
        jedis.zadd("z-set", 1.8, "18");
        jedis.zadd("z-set", 1.1, "11");

        // 元素数
        System.out.println(jedis.zcard("z-set"));  // 5
        System.out.println(jedis.zcount("z-set", 1.5, 2.5));  // 3

        // 按索引 、按score、按 member 取结果集
        System.out.println(jedis.zrange("z-set", 0, 10));  // [11, 15, 18, 21, 26]
        System.out.println(jedis.zrangeByScore("z-set", 1.5, 2.5));  // [15, 18, 21]
        System.out.println(jedis.zrangeByLex("z-set", "[15", "(21" ));  // [15, 18]

        // zrevrange 递减排列，zrevrangeByScore、zrevrangeByLex 一个意思
        System.out.println(jedis.zrevrange("z-set", 0, 10));
    }
}
