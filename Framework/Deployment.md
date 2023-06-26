# 部署

[参考博客](https://www.jianshu.com/p/ca2ba6fd498b) 参考[2](https://blog.csdn.net/dolpin_ink/article/details/123056852)

- 云服务器购买 --- 我使用的是aws ec2
- 打包 --- 
  - 前端vue项目
  - 后端项目打包。maven packaging， 注意要 setting-file encoding 改成UTF-8以防出问题

- 安装docker
- docker拉取镜像，部署mysql, redis, nginx, app, admin 然后进行服务编排，使项目按顺序启动。

## 云服务器

我使用了AWS 的EC2

所有安装使用yum

## 安装docker

[什么是docker](https://www.redhat.com/zh/topics/containers/what-is-docker)

[docker和虚拟机有什么区别](https://www.zhihu.com/question/48174633)

我的理解：可以简单理解成一个轻量级的虚拟机，但是docker不是虚拟机。如果使用虚拟机来跑我们的代码，我们需要安装各种依赖环境；使用docker，我们只需要拉取对应的镜像，让代码在对应镜像环境中进行运行就可以，各种依赖由docker来进行管理。

Docker通常用于隔离不同的应用，例如前端/后端/数据库

使用docker可以快速拉取镜像，加快部署速度

具体模块见其他笔记--

# 打包项目

后端代码需要我们手动打包并上传到服务器上来进行使用。

我们需要更改项目中的各种配置来达到和docker中的各个端口，ip地址相同。

![img](/Users/chengeping/Documents/LearningMaterial/Projects/myBlog/images/webp)

## 配置

### 后端

#### properties file

我们通过docker命令查询对应的docker对外端口

查看mysql

```bash
 docker inspect mysql
```

可以看到我对应的ip地址

![image-20220809171416833](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809171416833.png)

同样的，查看redis ip地址和端口

然后更改我们项目的properties 文件--- 我的项目是 application-prd.properties作为生产环境配置文件，并更改对应端口号和ip地址

- Application-prod.properties: 新增一个配置文件用来生产环境用，我们就不需要更改默认配置了。

- 更改 application的接口为==/api==，和前端相对应。！！！我。。错这儿了

- 更改mysql的连接-- 注意这里！！！还是得==3306==, 3307只是对外映射是3307，但是容器之间的访问还是3306

  ![image-20220810021711281](/Users/chengeping/Library/Application Support/typora-user-images/image-20220810021711281.png)

![image-20220809233547205](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809233547205.png)

#### 跨域配置

还有一个地方要改，局势WebMVCConfig中的跨域配置需要更改。

![image-20220809204645667](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809204645667.png)

### 前端

前端需要在对应的config文件中更改对应的自己服务器的 public ip，然后 npm run build，获得最终的dist文件

![image-20220809210241929](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809210241929.png)

## 打包

### blog-api

对应打包后端项目

选中对应要打包的模块，对应要读取的配置文件properties file，并进行打包

打包好后，在对应blog-api/target中可以找到我们打包好的jar包

复制并copy到对应的文件夹准备上传

![image-20220809204849354](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809204849354.png)

### blog -app

```bash
npm run build
```

打包获得对应的dist文件夹，给服务器使用。

## 上传文件

上传文件有两种方式

方式一：使用rz

~~~bash
sudo yum -y install lrzsz
~~~

复制对应文件在，在对应目录直接输入rz

方式二：使用scp copy文件

~~~ bash
scp -i "xxx.pem" xxxxx.zip public IP:/home/ec2-user/path
~~~

### blog-api

**copy jar 包**

后端对应目录位置在

`/mnt/docker/app`

```bash
sudo mkdir -p /mnt/docker/app  #创建对应目录

# 然后把对应的jar包复制到这个文件夹---我都放在 ~/upload 文件夹中，copy就好
mv ~/upload/blog-api-1.0-SNAPSHOT.jar ~/upload/blog_api.jar # 改成对应的我们之后的运行jar包名(配置在dockfile中的名字)
sudo mv blog_api.jar /mnt/docker/app/

cd /mnt/docker/app
```

**构建镜像配置文件**

```bash
sudo vim blog_dockerfile
```

```
FROM java:8   # 运行环境
MAINTAINER gepingchen   #作者信息
ADD ./blog_api.jar /app.jar  #build 的时候添加文件到image中
CMD java -jar /app.jar --spring.profiles.active=prod #容器启动命令
```

**构建**

```bash
docker build -f ./blog_dockerfile -t app .
```

此时查看docker镜像，可以看到，app开始运行

![image-20220809213911610](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809213911610.png)

### blog-app

前端对应目录位置在

`/mnt/gpchen/blog`

创建对应目录，并把对应文件copy到对应文件夹并解压缩

```bash
sudo mkdir -p /mnt/gpchen/blog
sudo mv ~/upload/dist.zip /mnt/gpchen/blog
sudo unzip dist.zip 
```

最后当前文件夹like this

![image-20220809215950197](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809215950197.png)



## 服务编排并启动服务

什么是[nginx](https://zhuanlan.zhihu.com/p/54793789)

服务编排是使用docker compose将springboot 和nginx分批次启动。

我们首先要启动nginx，然后启动springboot，也就是我们的项目。

> nginx的作用：①将服务器上的静态文件（如HTML、图片）通过HTTP协议展现给客户端。②反向代理服务器，这里nginx代理的就是前端资源客户端本来可以直接通过HTTP协议访问某网站应用服务器，网站管理员可以在中间加上一个Nginx，客户端请求Nginx，Nginx请求应用服务器，然后将结果返回给客户端。加一层代理可以实现**负载均衡、虚拟主机**等效果。

**安装 docker compose**

```bash
# Compose目前已经完全支持Linux、Mac OS和Windows，在我们安装Compose之前，需要先安装Docker。下面我 们以编译好的二进制包方式安装在Linux系统中。 
sudo curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
# 设置文件可执行权限 
sudo chmod +x /usr/local/bin/docker-compose
# 查看版本信息 
docker-compose -version
```

**配置docker-compoose.yml**

配置文件在 `/mnt/docker/docker-compose`

```bash
sudo mkdir /mnt/docker/docker-compose
cd /mnt/docker/docker-compose
sudo vim docker-compose.yml
```

```yml
version: '3'
services:
  nginx:
   image: nginx
   container_name: nginx
   ports:
    - 80:80
    - 443:443
   links:
    - app
   depends_on:
    - app
   volumes:
    - /mnt/docker/docker-compose/nginx/:/etc/nginx/
    - /mnt/gpchen/blog:/gpchen/blog
   network_mode: "bridge"  # 必须指定，不然会有网络错误问题，因为默认情况下mysql和docker不是在同一个网络下的，
  app:
    image: app
    container_name: app
    expose:
      - "8888"
    network_mode: "bridge"

```

- 先启动nginx
- links---链接到app模块
- depends_on ：依赖于app，意思就是app必须先启动，nginx才能启动
- volumes: 对应的容器
- app: 对应的镜像

### 配置nginx

Nginx 目录在 `/mnt/docker/docker-compose/nginx` 中

创建对应nginx目录

```bash
sudo mkdir /mnt/docker/docker-compose/nginx
```

这个目录下应该有三个文件

![image-20220809225042254](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809225042254.png)

**nginx.conf: nginx 配置文件**

```
user nginx;
worker_processes  1;
error_log  /var/log/nginx/error.log warn;
pid        /var/run/nginx.pid;
events {
    worker_connections  1024;
}
http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
    access_log  /var/log/nginx/access.log  main;
    sendfile        on;
    #tcp_nopush     on;
    keepalive_timeout  65;
    #gzip  on;
    include /etc/nginx/conf.d/*.conf;
}

```

**conf.d 文件夹**

新建这个文件夹，修改对应的blog的配置，上面nginx配置 中最后一行就标记了，会读取这个文件夹中的文件，并进行映射。

创建对应的 blog.conf 文件

```bash
sudo vim blog.conf
```

**添加对应的nginx 映射**

- Upstream 也就是对应nginx 依赖的上流是app 模块，端口是我们配置的8888
- server是我们的nginx代理服务
  - /api： 也就是我们的后端，对应的我们定义的upstream
  - location：对应的是我们的源代码
    - location api/ 对应了我们的后端接口
    - Location 对应了我们的前端接口
    - location 剩下的一个部分是允许所有的前端的访问资源可以背访问

```bash
gzip_min_length 1k;
gzip_buffers 4 16k;
gzip_comp_level 2;
gzip_vary off;
upstream appstream{ 
    server app:8888;
}
server{
    listen 80;
    server_name localhost; # 没有域名的配置方法
    # server_name 域名名称
    # backend
    location /api {
        proxy_pass http://appstream;
    }
    # frontend
    location / {
        root /gpchen/blog/;
        index index.html;
    }
    location ~* \.(jpg|jpeg|gif|png|swf|rar|zip|css|js|map|svg|woff|ttf|txt)$ {
         root /gpchen/blog/;
         index index.html;
         add_header Access-Control-Allow-Origin *;
     }
}

```

**Mime.types** 

最后还需要一个这个文件，这个文件网上下载一个就可以直接使用了

```

types {
    text/html                                        html htm shtml;
    text/css                                         css;
    text/xml                                         xml;
    image/gif                                        gif;
    image/jpeg                                       jpeg jpg;
    application/javascript                           js;
    application/atom+xml                             atom;
    application/rss+xml                              rss;

    text/mathml                                      mml;
    text/plain                                       txt;
    text/vnd.sun.j2me.app-descriptor                 jad;
    text/vnd.wap.wml                                 wml;
    text/x-component                                 htc;

    image/png                                        png;
    image/svg+xml                                    svg svgz;
    image/tiff                                       tif tiff;
    image/vnd.wap.wbmp                               wbmp;
    image/webp                                       webp;
    image/x-icon                                     ico;
    image/x-jng                                      jng;
    image/x-ms-bmp                                   bmp;

    font/woff                                        woff;
    font/woff2                                       woff2;

    application/java-archive                         jar war ear;
    application/json                                 json;
    application/mac-binhex40                         hqx;
    application/msword                               doc;
    application/pdf                                  pdf;
    application/postscript                           ps eps ai;
    application/rtf                                  rtf;
    application/vnd.apple.mpegurl                    m3u8;
    application/vnd.google-earth.kml+xml             kml;
    application/vnd.google-earth.kmz                 kmz;
    application/vnd.ms-excel                         xls;
    application/vnd.ms-fontobject                    eot;
    application/vnd.ms-powerpoint                    ppt;
    application/vnd.oasis.opendocument.graphics      odg;
    application/vnd.oasis.opendocument.presentation  odp;
    application/vnd.oasis.opendocument.spreadsheet   ods;
    application/vnd.oasis.opendocument.text          odt;
    application/vnd.openxmlformats-officedocument.presentationml.presentation
                                                     pptx;
    application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
                                                     xlsx;
    application/vnd.openxmlformats-officedocument.wordprocessingml.document
                                                     docx;
    application/vnd.wap.wmlc                         wmlc;
    application/x-7z-compressed                      7z;
    application/x-cocoa                              cco;
    application/x-java-archive-diff                  jardiff;
    application/x-java-jnlp-file                     jnlp;
    application/x-makeself                           run;
    application/x-perl                               pl pm;
    application/x-pilot                              prc pdb;
    application/x-rar-compressed                     rar;
    application/x-redhat-package-manager             rpm;
    application/x-sea                                sea;
    application/x-shockwave-flash                    swf;
    application/x-stuffit                            sit;
    application/x-tcl                                tcl tk;
    application/x-x509-ca-cert                       der pem crt;
    application/x-xpinstall                          xpi;
    application/xhtml+xml                            xhtml;
    application/xspf+xml                             xspf;
    application/zip                                  zip;

    application/octet-stream                         bin exe dll;
    application/octet-stream                         deb;
    application/octet-stream                         dmg;
    application/octet-stream                         iso img;
    application/octet-stream                         msi msp msm;

    audio/midi                                       mid midi kar;
    audio/mpeg                                       mp3;
    audio/ogg                                        ogg;
    audio/x-m4a                                      m4a;
    audio/x-realaudio                                ra;

    video/3gpp                                       3gpp 3gp;
    video/mp2t                                       ts;
    video/mp4                                        mp4;
    video/mpeg                                       mpeg mpg;
    video/quicktime                                  mov;
    video/webm                                       webm;
    video/x-flv                                      flv;
    video/x-m4v                                      m4v;
    video/x-mng                                      mng;
    video/x-ms-asf                                   asx asf;
    video/x-ms-wmv                                   wmv;
    video/x-msvideo                                  avi;
}

```

# 运行测试

在对应的docker-compose 文件夹，运行docker-compose进行编排, 就可以启动我们的服务了。

```bash
docker-compose up #直接启动

docker-compose up -d #代表后台启动

docker-compose down  #停止并删除容器

docker-compose start #启动已有容器

docker-compose stop  #停止运行的容器

```

- 查看ngix日志：`docker logs nginx`
- 查看后端日志：`docker-compose logs`



我们要停止服务的时候，需要下面这个顺序

查看所有镜像（包括停止的）

```
docker ps -a 
```

![image-20220809232524639](/Users/chengeping/Library/Application Support/typora-user-images/image-20220809232524639.png)

删除对应的app，重新构建

```bash
docker rm xxxID
```

#  summary

启动顺序应该是 mysql, redis, app, 最后启动nginx

## 操作顺序

打包

```bash
scp -i "EC2.pem" toUpload/blog-api-1.0-SNAPSHOT.jar ec2-user@ec2-3-233-42-225.compute-1.amazonaws.com:/home/ec2-user/upload
scp -i "EC2.pem" toUpload/dist.zip ec2-user@ec2-3-233-42-225.compute-1.amazonaws.com:/home/ec2-user/upload
```

复制到对应位置

```
cd ~/upload/
mv ~/upload/blog-api-1.0-SNAPSHOT.jar ~/upload/blog_api.jar
sudo mv blog_api.jar /mnt/docker/app/
cd /mnt/docker/app
```

```
cd ~/upload/
sudo mv ~/upload/dist.zip /mnt/gpchen/blog
cd /mnt/gpchen/blog
sudo rm index.html 
sudo rm static/
sudo unzip dist.zip 
cd dist/
sudo mv * ../
cd ..
sudo rm -r dist
```

删除原有app

```
docker ps -a
docker rm nginxID appID
docker ps -a
```

构建app

```
cd /mnt/docker/app
docker build -f /mnt/docker/app/blog_dockerfile -t app .
```

启动docker-compose服务

```
cd  /mnt/docker/docker-compose 
docker-compose up
```





```
gzip_min_length 1k;
gzip_buffers 4 16k;
gzip_comp_level 2;
gzip_vary off;
upstream appstream{ 
    server app:8888;
}
server{
    listen 80;
    server_name localhost; # 没有域名的配置方法
    # server_name 域名名称
    # backend
    location /api {
        proxy_pass http://appstream;
    }
    # frontend
    location / {
        root /capstone/ridenshare/;
        index index.html;
        try_files  $uri $uri/ /index.html;
    }
    location ~* \.(jpg|jpeg|gif|png|swf|rar|zip|css|js|map|svg|woff|ttf|txt)$ {
         root /capstone/ridenshare/;
         index index.html;
         add_header Access-Control-Allow-Origin *;
     }
}


```



```
gzip_min_length 1k;
gzip_buffers 4 16k;
gzip_comp_level 2;
gzip_vary off;
upstream appstream{
    server app:8888;
}
server{ #frontend
    listen 80;
    server_name www.ridenshare.org;
    # frontend
    location / {
        root /capstone/ridenshare/;
        index index.html;
        try_files  $uri $uri/ /index.html;
    }
    location ~* \.(jpg|jpeg|gif|png|swf|rar|zip|css|js|map|svg|woff|ttf|txt)$ {
         root /capstone/ridenshare/;
         index index.html;
         add_header Access-Control-Allow-Origin *;
     }
}
server{
    # backend
    listen 8888;
    server_name www.ridenshare.org;
    location /api {
        proxy_pass http://appstream;
        proxy_cookie_path / /;
        proxy_set_header Cookie $http_cookie;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X_Forward-For $proxy_add_x_forwarded_for;
    }
    location ~* \.(jpg|jpeg|gif|png|swf|rar|zip|css|js|map|svg|woff|ttf|txt)$ {
         root /capstone/ridenshare/;
         index index.html;
         add_header Access-Control-Allow-Origin *;
     }
}
```



# 遇到的错误

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



## 其他设置

docker配置rocker mq. 因为这也是spring项目，所以我使用了另一个instance

docker 拉取

```bash
docker pull foxiswho/rocketmq:4.8.0
```

注意，logs必须设置777权限不然启动不成功

有log目录启动name server

```bash
docker run -d -v /usr/local/rocketmq/logs:/opt/docker/rocketmq/logs \
      --name rmqnamesrv \
      -e "JAVA_OPT_EXT=-Xms512M -Xmx512M -Xmn128m" \
      -p 9876:9876 \
      foxiswho/rocketmq:4.8.0 \
      sh mqnamesrv
docker run -d -v /mnt/docker/rocketmq/logs:/opt/docker/rocketmq/logs \
      --name rmqnamesrv \
      -e "JAVA_OPT_EXT=-Xms512M -Xmx512M -Xmn128m" \
      -p 9876:9876 \
      foxiswho/rocketmq:4.8.0 \
      sh mqnamesrv
```

配置 

```
brokerIP1=54.89.80.177
namesrvAddr=54.89.80.177:9876
brokerName=broker_all
```

Broker 目录映射

```bash
docker run -d  -v /opt/docker/rocketmq/logs:/usr/local/rocketmq/logs -v /opt/docker/rocketmq/store:/usr/local/rocketmq/store \
      -v /opt/docker/rocketmq/conf:/usr/local/rocketmq/conf \
      --name rmqbroker \
      -e "NAMESRV_ADDR=54.89.80.177:9876" \
      -e "JAVA_OPT_EXT=-Xms512M -Xmx512M -Xmn128m" \
      -p 10911:10911 -p 10912:10912 -p 10909:10909 \
      foxiswho/rocketmq:4.8.0 \
      sh mqbroker -c /usr/local/rocketmq/conf/broker.properties
      
docker run -d  -v /opt/docker/rocketmq/logs:/mnt/docker/rocketmq/logs -v /opt/docker/rocketmq/store:/mnt/docker/rocketmq/store \
      -v /opt/docker/rocketmq/conf:/mnt/docker/rocketmq/conf \
      --name rmqbroker \
      -e "NAMESRV_ADDR=54.89.80.177:9876" \
      -e "JAVA_OPT_EXT=-Xms512M -Xmx512M -Xmn128m" \
      -p 10911:10911 -p 10912:10912 -p 10909:10909 \
      foxiswho/rocketmq:4.8.0 \
      sh mqbroker -c /opt/docker/rocketmq/conf/broker.conf
```

```
docker run --name rmqconsole --link rmqnamesrv:rmqnamesrv \
-e "JAVA_OPTS=-Drocketmq.namesrv.addr=54.89.80.177:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" \
-p 8180:8080 -t styletang/rocketmq-console-ng
```



```yaml
version: '3.5'
services:
  rmqnamesrv:
    image: foxiswho/rocketmq:server
    container_name: rmqnamesrv
    ports:
      - 9876:9876
    volumes:
      - /usr/local/rocketmq/logs:/opt/logs
      - /usr/local/rocketmq/store:/opt/store
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
    volumes:
      - /usr/local/rocketmq/logs:/opt/logs
      - /usr/local/rocketmq/store:/opt/store
      - /usr/local/rocketmq/conf/broker.conf:/etc/rocketmq/broker.conf
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