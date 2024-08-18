# SSO

[原理](https://www.jianshu.com/p/75edcc05acfd)

https://djangocas.dev/blog/cas-101-introduction-to-cas-central-authentication-service/#cas-introduction

## CAS 

[官网](https://apereo.github.io/cas/6.6.x/index.html)

[原理](https://www.jianshu.com/p/a58c559bf0e1)

[Documentation](https://apereo.github.io/cas/6.6.x/index.html)

![CAS Architecture Diagram](https://apereo.github.io/cas/6.6.x/images/cas_architecture.png)

### Problem

我们的服务需要通过学校认证为学生/老师，因此需要调用学校的服务接口。

学校的接口为：https://login.cs.vt.edu/cas 

学校的服务接口为了安全，使用的是https，因此需要TLS证书。

TLS证书需要我们手动生成并上传给学校进行验证。

[Java中添加TLS证书支持方式（Springboot）](https://blog.51cto.com/u_14482423/3070740)

[Springboot配置CAS client方式-example](https://blog.csdn.net/uziuzi669/article/details/119486588)

[example -2 ](https://blog.csdn.net/yucaifu1989/article/details/121538546?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-121538546-blog-112994478.pc_relevant_default&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-121538546-blog-112994478.pc_relevant_default&utm_relevant_index=1)

