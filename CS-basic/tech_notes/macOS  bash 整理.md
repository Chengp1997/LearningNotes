## macOS  bash 整理

macOS自Catalina系统开始，shell默认不再使用bash而是系统默认使用zsh。

通过以下命令可以查看系统带的shell有什么

```shell
cat /etc/shells
```

检查当前使用的shell

```shell
echo $SHELL         
/bin/zsh
```

可以手动更改shell，推荐zsh

```bash
chsh -s /bin/zsh
```



有些指令不太一样这里记录一下。

1. ### 环境变量

如果默认使用的是bash，则系统默认读取环境变量的时候使用的是.bash_profile，因此如果将环境变量配置到.bash_profile会导致有些指令读取不到。此时应该使用如下的方式。

> - **.zprofile** 的作用与 **.bash_profile** 相同，并且在登录时运行（包括通过 SSH 运行）
> - **.zshrc** 的作用与 **.bashrc** 相同，并针对每个新的“终端”会话运行

```zsh
vim ~/.zshrc    //修改环境变量
source ~/.zshrc.  环境变量生效
```

2. ### 安装oh-my-zsh

如果~/.zshrc文件不存在，可以安装此插件。

> https://github.com/ohmyzsh/ohmyzsh   官方github文档

也可通过git来安装

```shell

```



### 安装brew

```shell
/bin/zsh -c "$(curl -fsSL https://gitee.com/cunkai/HomebrewCN/raw/master/Homebrew.sh)"
```

### 安装npm

```shell
brew install npm
```

npm提速

方法一：使用淘宝定制的cnpm命令行工具替代默认安装npm

```shell
npm install -g cnpm --registry=https://registry.npm.taobao.org
```

方法二：将npm默认的下载地址修改为淘宝镜像

```shell
npm cofig set registry https://registry.npm.taobao.org/
```

注意，如果将来你需要发布自己的软件包时，需要将registry字段的值修改回来

```shell
npm cofig set registry https://registry.npmjs.org/
```

### 安装assr（解压缩工具）

```shell
npm install -g asar
```

解压缩指令

```shell
asar extract app.asar app   //  文件名。解压缩文件夹名
```

打包

```shell
asar pack app app.asar
```

