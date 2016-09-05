package jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * test jedis
 *
 * Created by miaoxinguo on 2016/9/5.
 */
public class TestJedis {
    Jedis jedis = new Jedis();

    @Test
    public void testBaseTransaction() {
        Transaction tx = jedis.multi();
        // do something
        tx.exec();
    }

    @Test
    public void testTransaction() {
        Transaction tx = jedis.multi();
        Response resp = tx.watch("");

    }
}
