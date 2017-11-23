package com;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 彦祖 .
 */
public class JedisTest {


    private static void test1(){
        Jedis jedis = new Jedis("172.16.2.166",6380,3000,3000);//连接超时和读写超时
        jedis.set("key1","value1");
        jedis.set("key2","value2");
        System.out.println(jedis.incr("key3"));
        String xxx = jedis.get("key1");
        System.out.println(xxx);
    }

    private static void test2(){
        Jedis jedis = new Jedis("172.16.2.166",6380,3000,3000);//连接超时和读写超时
        jedis.hset("key4","field1","1");
        jedis.hset("key4","field2","2");
        jedis.hget("key4","field1");
        Map map = jedis.hgetAll("key4");
//        System.out.println(xxx);
    }

    private static void test3(){
        Jedis jedis = new Jedis("172.16.2.166",6380,3000,3000);//连接超时和读写超时
        jedis.rpush("mylist","1");
        jedis.rpush("mylist","2");
        jedis.rpush("mylist","3");
        List<String> list = jedis.lrange("mylist",0,-1);
    }

    private static void test4(){
        Jedis jedis = new Jedis("172.16.2.166",6380,3000,3000);//连接超时和读写超时
        jedis.sadd("myset","a");
        jedis.sadd("myset","b");
        jedis.sadd("myset","c");
        Set<String> set = jedis.smembers("myset");
        jedis.close();
    }

    private static void test5(){
        Jedis jedis = new Jedis("172.16.2.166",6380,3000,3000);//连接超时和读写超时
        jedis.zadd("myzset",99,"tom");
        jedis.zadd("myzset",66,"peter");
        jedis.zadd("myzset",33,"james");
        Set<String> set = jedis.zrange("mysset",0,-1);
    }

    private static void test6(){
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig,"172.16.2.166",6380);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set("hello","world!!!!!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null){
                //如果使用jedisPool获取jedis，则close不是关闭连接，而是代表归还连接池
                jedis.close();
            }
        }
    }

    private static void pipleLine(){
        Jedis jedis = new Jedis("172.16.2.166",6380,3000,3000);//连接超时和读写超时
        for(int i = 0 ; i < 100 ;i++){
            Pipeline pipeline = jedis.pipelined();
            for (int j = i*100;j<(i+1)*100;j++){
                pipeline.hset("key8","field1","value1");
            }
            List list = pipeline.syncAndReturnAll();
        }
    }



    public static void main(String[] args) {
        pipleLine();
    }

}
