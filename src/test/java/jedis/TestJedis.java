package jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * test jedis
 *
 * Created by miaoxinguo on 2016/9/5.
 */
public class TestJedis {

    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis("121.42.58.92");
        jedis.auth("isiagvK6TQqCt84");
    }


    @Test
    public void testBaseTransaction() {
        Transaction tx = jedis.multi();
        jedis.set("test_aaa", "20");
        List<Object> list = tx.exec();
    }

    @Test
    public void testTransaction() {
        Transaction tx = jedis.multi();
        Response resp = tx.watch("");
    }

    @Test
    public void testGetString() {
        System.out.println(jedis.get("*"));
    }
}
