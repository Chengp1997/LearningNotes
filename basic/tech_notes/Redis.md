# Redis

## 安装

https://www.cnblogs.com/yanguobin/p/11446967.html

## 指令

### 连接

**服务端**：

```
redis-server
```

**客户端**

*本地*

```
redis-cli
```

*远程*

```
redis-cli -h host -p port -a password
```

**检测服务是否启动**

对于客户端可以：

```
127.0.0.1:6379> ping
PONG
```



### 集合 操作

#### String

Set key value

```
127.0.0.1:6379> set string1 hello
OK
127.0.0.1:6379> get string1
"hello"
```

#### hashmap

HMGET key field1 value filed2 value filedn value……

HGET key

```
127.0.0.1:6379> hmset key1 fi1 1 fi2 2
OK
127.0.0.1:6379> HGET key1 fi1
"1"
```

#### list

LPUSH key element

LRANGE key start stop

```
127.0.0.1:6379> lpush list first
(integer) 1
127.0.0.1:6379> lpush list second
(integer) 2
127.0.0.1:6379> lpush list third
(integer) 3
127.0.0.1:6379> lrange list 0 5
1) "third"
2) "second"
3) "first"
```

#### set

sadd key value. 成功返回1，失败返回0

smembers key

```
127.0.0.1:6379> sadd set first
(integer) 1
127.0.0.1:6379> sadd set second
(integer) 1
127.0.0.1:6379> sadd set third
(integer) 1
127.0.0.1:6379> sadd set second
(integer) 0
127.0.0.1:6379> smembers set
1) "second"
2) "first"
3) "third"

```

#### zset

zadd key score member

zset 是有序的set。

不同的是每个元素都会关联一个double类型的score。redis正是通过score来为集合中的成员进行从小到大的排序。

zset的成员是唯一的,但分数(score)却可以重复。

```
127.0.0.1:6379> zadd zset 0 first
(integer) 1
127.0.0.1:6379> zadd zset 0 second
(integer) 1
127.0.0.1:6379> zadd zset 0 third 
(integer) 1
127.0.0.1:6379> zrangebyscore zset 0 5
1) "first"
2) "second"
3) "third"
127.0.0.1:6379> zadd zset 1 second
(integer) 0
127.0.0.1:6379> zrangebyscore zset 0 5
1) "first"
2) "third"
3) "second"
127.0.0.1:6379> zadd zset 2 first
(integer) 0
127.0.0.1:6379> zrangebyscore zset 0 5
1) "third"
2) "second"
3) "first"
```

#### hyperloglog

新引入的结构，用来做基数计算

> 什么事基数
>
> 比如数据集 {1, 3, 5, 7, 5, 7, 8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。 基数估计就是在误差可接受的范围内，快速计算基数。

### 订阅发布功能

订阅某频道。当有消息传给某频道，所有订阅的用户都会收到。

cli1-订阅

```
127.0.0.1:6379> subscribe channel1
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "channel1"
3) (integer) 1
```

cli2 -发消息

```
127.0.0.1:6379> publish channel1 "hello baby"
(integer) 1
```

此时，cli1会收到

```
1) "message"
2) "channel1"
3) "hello baby"
```

### 事务功能

单个 Redis 命令的执行是原子性的，但 Redis 没有在事务上增加任何维持原子性的机制，所以 **Redis 事务的执行并不是原子性的**。

意味着，如果中间有指令失败，并不会造成回滚，而会继续执行。

由MULTI开始 EXEC执行开始知道结束。

```
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379> set string1 halo
QUEUED
127.0.0.1:6379> get string1
QUEUED
127.0.0.1:6379> set string1 goodbye
QUEUED
127.0.0.1:6379> get string1
QUEUED
127.0.0.1:6379> EXEC
1) OK
2) "halo"
3) OK
4) "goodbye"
```

### 脚本的使用

内置使用LUA解释器来执行脚本

