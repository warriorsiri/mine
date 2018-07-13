package com.warrior.demo.redis.test;

import redis.clients.jedis.Jedis;

public class RedisTest {

	public static void main(String[] args) {
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("localhost");
		System.out.println("连接成功");
		// 查看服务是否运行
		System.out.println("服务正在运行: " + jedis.ping());

		// 字符串测试
		jedis.set("hello", "world");
		System.out.println("存储的字符为：" + jedis.get("hello"));

		jedis.del("hello");
		System.out.println("存储的字符为：" + jedis.get("hello"));

		jedis.setex("expirekey", 5, "五秒后过期");
		System.out.println("expirekey-value：" + jedis.get("expirekey"));

		jedis.setex("expirekey", 10, "五秒后过期");

		// 列表测试
		jedis.lpush("list-key", "aaa", "bbb", "ccc", "ddd");
		System.out.println("list-key的长度：" + jedis.llen("list-key"));
		jedis.lpop("list-key");
		System.out.println(jedis.lrange("list-key", 0, -1));
		// 设置过期时间
		jedis.expire("list-key", 5);
		// 续命
		jedis.expire("list-key", 20);

		// set测试
		jedis.sadd("set-key", "111", "222", "333", "444", "555", "111");
		jedis.sadd("set-key", "666");
		System.out.println("集合的长度：" + jedis.scard("set-key"));
		System.out.println("集合的元素：" + jedis.smembers("set-key"));
		jedis.sadd("set-key", "666");
		System.out.println("删除元素个数：" + jedis.srem("set-key", "333", "222", "777"));
		System.out.println("删除后集合的长度：" + jedis.scard("set-key"));
		System.out.println("删除后集合的元素：" + jedis.smembers("set-key"));

		// hash测试
		jedis.hset("hash", "name", "warrior");
		jedis.hset("hash", "addr", "12345");
		jedis.hset("hash", "sex", "M");
		System.out.println("获取hash所有字段：" + jedis.hgetAll("hash"));
		System.out.println("获取hash的name字段：" + jedis.hget("hash", "name"));
		System.out.println("删除hash的name字段：" + jedis.hdel("hash", "name"));
		System.out.println("获取hash的name字段：" + jedis.hget("hash", "name"));

		// 排序set的测试
		jedis.zadd("scoreset", 100, "aa");
		jedis.zadd("scoreset", 90, "bb");
		jedis.zadd("scoreset", 80, "cc");
		jedis.zadd("scoreset", 70, "dd");
		jedis.zadd("scoreset", 60, "dd");
		jedis.zadd("scoreset", 60, "ee");
		System.out.println("获取scoreset所有字段：" + jedis.zrange("scoreset", 0, -1));
		System.out.println("获取scoreset所有字段，包含分数：" + jedis.zrangeWithScores("scoreset", 0, -1));
		System.out.println("获取scoreset,70-90分的元素：" + jedis.zrangeByScore("scoreset", 70, 90));
		System.out.println("删除cc字段：" + jedis.zrem("scoreset", "cc"));
		System.out.println("获取scoreset所有字段：" + jedis.zrange("scoreset", 0, -1));
		System.out.println("统计70-90分的元素个数：" + jedis.zcount("scoreset", 70, 90));
	}

}
