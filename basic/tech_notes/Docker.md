# Docker

## 常用命令记录

~~~bash 
# 安装
sudo yum update -y
sudo yum install -y docker
~~~

```bash
# 启动服务
sudo service docker start
```

```bash
# 将 `ec2-user` 添加到 `docker` 组，以便您能够执行 Docker 命令，而无需使用 `sudo`
sudo usermod -a -G docker ec2-user
# 更新用户组
newgrp docker
# 验证 `ec2-user` 是否能在没有 `sudo` 的情况下运行 Docker 命令。
docker info
```

```bash
# 停止服务
sudo service docker stop
```

**镜像操作**

```bash
# 搜索镜像
docker search xxxx（mysql之类的）
# 拉取镜像
docker pull mysql:version
# 查看所有镜像
docker images
# 删除对应镜像
docker rmi xxxx(image ID)
# 查看当前镜像对应的ip地址
docker inspect redis
```

**容器操作**

创建容器见下面具体模块

```bash
# 查看所有容器，包括停止
docker ps -a
# 删除容器
# 运行前必须先停止
docker stop $(docker ps -a -q)  
# 删除所有停止容器
docker rm $(docker ps -a -q)
# 进入容器
docker exec -it NAME bash
```

## 运行测试

```bash
docker-compose up #直接启动

docker-compose up -d #代表后台启动

docker-compose down  #停止并删除容器

docker-compose start #启动已有容器

docker-compose stop  #停止运行的容器

docker logs NAME # 查看日志

docker-compose logs # 查看编排服务的日志
```

## 常用镜像

```
docker pull nginx
docker pull redis:5.0.3
docker pull java:8
docker pull mysql:5.7
```

## Docker-composer

服务编排工具，可以让服务按批次启动

```bash
# Compose目前已经完全支持Linux、Mac OS和Windows，在我们安装Compose之前，需要先安装Docker。下面我 们以编译好的二进制包方式安装在Linux系统中。 
sudo curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
# 设置文件可执行权限 
sudo chmod +x /usr/local/bin/docker-compose
# 查看版本信息 
docker-compose -version
```

此时，构建docker-compose.yml ，在当前目录下执行就可以进行服务编排



# Container

我的所有docker文件都保存在 `/mnt/docker`中

## Mysql

### Setup

创建对应文件夹

```bash
sudo mkdir /mnt/docker/mysql
cd /mnt/docker/mysql
```

创建对应文件夹

```bash
sudo mkdir /mnt/docker/mysql/conf
sudo mkdir /mnt/docker/mysql/logs
sudo mkdir /mnt/docker/mysql/data
```

创建容器(run)/设置对应的端口映射，目录映射, 默认密码为root

```bash
docker run -id \
-p 3307:3306 \
--name=mysql \
--network-alias mysql \
-v /mnt/docker/mysql/conf:/etc/mysql/conf.d \
-v /mnt/docker/mysql/logs:/logs \
-v /mnt/docker/mysql/data:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=root \
mysql:5.7
```

创建对应的配置文件 /mnt/docker/mysql/conf

```bash
[mysqld]
##
## Remove leading ## and set to the amount of RAM for the most important data
## cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
## innodb_buffer_pool_size = 128M
##
## Remove leading ## to turn on a very important data integrity option: logging
## changes to the binary log between backups.
## log_bin
##
## Remove leading ## to set options mainly useful for reporting servers.
## The server defaults are faster for transactions and fast SELECTs.
## Adjust sizes as needed, experiment to find the optimal values.
## join_buffer_size = 128M
## sort_buffer_size = 2M
## read_rnd_buffer_size = 2M
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
character-set-server=utf8
## Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0
lower_case_table_names=1
pid-file=/var/run/mysqld/mysqld.pid
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION

```

### Enter 

进入容器

```bash
docker exec -it mysql bash
```

连接mysql

```bash
mysql -h 127.0.0.1 -u root -p
```

接下来就是正常使用mysql来执行

**执行docker中的mysql文件**

因为之前我们已经挂载过了，所以只要上传到对应的`/mnt/docker/mysql/data/`对应的docker文件夹`/var/lib/mysql`中就会存在

我在 `/mnt/docker/mysql/data/`中创建了文件夹sql_files, 对应的`/var/lib/mysql`中也会存在这个文件夹，上传文件到这个文件夹中，并导入数据库

```bash
docker exec -it mysql bash # 进入容器
cd /var/lib/mysql/sql_files #切换到对应文件夹
mysql -h 127.0.0.1 -u root -p < blog.sql  #导入
```

进入数据库-执行并保存

```bash
mysql -h 127.0.0.1 -u root -p
```

```sql
source blog.sql;
use DB_NAME;
show tables;
select * from sys_user
```

### problem

问题1:

进入mysql：这里遇到了<img src="/Users/chengeping/Library/Application Support/typora-user-images/image-20220809173543181.png" alt="image-20220809173543181" style="zoom:50%;" />的问题，因为mysql client 要连到 mysqld.sock ，但mysqld.sock 只存在docker container中。

解决方案：增加127.0.0.1连接

## redis

```bash
docker run -id --name=redis -p 6379:6379 redis:5.0.3
```

## rockermq

**参考博客**

docker 拉取

```bash
docker pull foxiswho/rocketmq:4.8.0
```

注意，logs必须设置777权限不然启动不成功

需要建立文件broker.conf以及文件夹如下

```
docker-compose.yml
conf
	- broker.conf
logs
store
```

因为服务牵连复杂，使用docker-comopose来编排

```yml
version: '3.5'
services:
  rmqnamesrv:
    image: foxiswho/rocketmq:server
    container_name: rmqnamesrv
    ports:
      - 9876:9876
    volumes:
      - /mnt/docker/rocketmq/logs:/opt/logs
      - /mnt/docker/rocketmq/store:/opt/store
    networks:
        rmq:
          aliases:
            - rmqnamesrv

  rmqbroker:
    image: foxiswho/rocketmq:broker
    container_name: rmqbroker
    ports:
      - 10909:10909
      - 10911:10911
      - 10912:10912
    volumes:
      - /mnt/docker/rocketmq/logs:/opt/logs
      - /mnt/docker/rocketmq/store:/opt/store
      - /mnt/docker/rocketmq/conf/broker.conf:/etc/rocketmq/broker.conf
    environment:
        NAMESRV_ADDR: "rmqnamesrv:9876"
        JAVA_OPTS: " -Duser.home=/opt"
        JAVA_OPT_EXT: "-server -Xms128m -Xmx128m -Xmn128m"
    command: mqbroker -c /etc/rocketmq/broker.conf
    depends_on:
      - rmqnamesrv
    networks:
      rmq:
        aliases:
          - rmqbroker

  rmqconsole:
    image: styletang/rocketmq-console-ng
    container_name: rmqconsole
    ports:
      - 8180:8080
    environment:
        JAVA_OPTS: "-Drocketmq.namesrv.addr=rmqnamesrv:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false"
    depends_on:
      - rmqnamesrv
    networks:
      rmq:
        aliases:
          - rmqconsole

networks:
  rmq:
    name: rmq
    driver: bridge
```

Conf 文件

```properties
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.


# 所属集群名字
brokerClusterName=DefaultCluster

# broker 名字，注意此处不同的配置文件填写的不一样，如果在 broker-a.properties 使用: broker-a,
# 在 broker-b.properties 使用: broker-b
brokerName=broker_all

# 0 表示 Master，&gt; 0 表示 Slave
brokerId=0

# nameServer地址，分号分割
# namesrvAddr=rocketmq-nameserver1:9876;rocketmq-nameserver2:9876
namesrvAddr=54.89.80.177:9876

# 启动IP,如果 docker 报 com.alibaba.rocketmq.remoting.exception.RemotingConnectException: connect to &lt;192.168.0.120:10909&gt; failed
# 解决方式1 加上一句 producer.setVipChannelEnabled(false);，解决方式2 brokerIP1 设置宿主机IP，不要使用docker 内部IP
brokerIP1=54.89.80.177

# 在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4

# 是否允许 Broker 自动创建 Topic，建议线下开启，线上关闭 ！！！这里仔细看是 false，false，false
autoCreateTopicEnable=true

# 是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true

# Broker 对外服务的监听端口
listenPort=10911

# 删除文件时间点，默认凌晨4点
deleteWhen=04

# 文件保留时间，默认48小时
fileReservedTime=120

# commitLog 每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824

# ConsumeQueue 每个文件默认存 30W 条，根据业务情况调整
mapedFileSizeConsumeQueue=300000

# destroyMapedFileIntervalForcibly=120000
# redeleteHangedFileInterval=120000
# 检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
# 存储路径
# storePathRootDir=/home/ztztdata/rocketmq-all-4.1.0-incubating/store
# commitLog 存储路径
# storePathCommitLog=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/commitlog
# 消费队列存储
# storePathConsumeQueue=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/consumequeue
# 消息索引存储路径
# storePathIndex=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/index
# checkpoint 文件存储路径
# storeCheckpoint=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/checkpoint
# abort 文件存储路径
# abortFile=/home/ztztdata/rocketmq-all-4.1.0-incubating/store/abort
# 限制的消息大小
maxMessageSize=65536

# flushCommitLogLeastPages=4
# flushConsumeQueueLeastPages=2
# flushCommitLogThoroughInterval=10000
# flushConsumeQueueThoroughInterval=60000

# Broker 的角色
# - ASYNC_MASTER 异步复制Master
# - SYNC_MASTER 同步双写Master
# - SLAVE
brokerRole=ASYNC_MASTER

# 刷盘方式
# - ASYNC_FLUSH 异步刷盘
# - SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH

# 发消息线程池数量
# sendMessageThreadPoolNums=128
# 拉消息线程池数量
# pullMessageThreadPoolNums=128
```



# 遇到过的问题

问题一

![image-20220810021306012](/Users/chengeping/Library/Application Support/typora-user-images/image-20220810021306012.png)

如果满了，执行 docker system prune

重启镜像 docker run -d  xx



问题二: 如果有docker端口占用问题

```bash
 sudo service docker stop
 sudo rm /var/lib/docker/network/files/local-kv.db
 sudo service docker start
```

