package com;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class JedisSentinelTest {

    public static void main(String[] args) {
        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("127.0.0.1:36379");
        sentinels.add("127.0.0.1:36380");
        sentinels.add("127.0.0.1:36381");
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName,sentinels);

        int counter = 0;
        while (true){
            counter++;
            Jedis jedis = null;
            try {
                jedis = jedisSentinelPool.getResource();
                int index = new Random().nextInt(100000);
                String key = "k-"+index;
                String value = "value"+index;
                jedis.set(key,value);
                if(counter%100 == 0 ){
                    System.out.println("key is "+ key+" , value = "+value);
                }
                TimeUnit.MICROSECONDS.sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(jedis != null){
                    jedis.close();//这里的close是归还连接池的意思
                }
            }

        }
    }
}
