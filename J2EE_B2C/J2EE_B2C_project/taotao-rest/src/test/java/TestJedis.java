import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	
	@Test
	public void testJedisCluster(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster cluster = (JedisCluster) context.getBean("redisClient");
		cluster.set("mbc", "This is Jedis");
		
		System.out.println(cluster.get("mbc"));
		
	}
	@Test
	public void testJedisCluster1() {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("172.16.95.128", 7001));
		nodes.add(new HostAndPort("172.16.95.128", 7002));
		nodes.add(new HostAndPort("172.16.95.128", 7003));
		nodes.add(new HostAndPort("172.16.95.128", 7004));
		nodes.add(new HostAndPort("172.16.95.128", 7005));
		nodes.add(new HostAndPort("172.16.95.128", 7006));
		
		JedisCluster cluster = new JedisCluster(nodes);
		
		cluster.set("key1", "1000");
		String string = cluster.get("key1");
		System.out.println(string);
		
		cluster.close();
	}
	
	/**
	 * 使用连接池
	 */
	@Test
	public void testJedisPool() {
		//创建jedis连接池
		JedisPool pool = new JedisPool("172.16.95.128", 6379);
		//从连接池中获得Jedis对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jedis对象
		jedis.close();
		pool.close();
	}
}
