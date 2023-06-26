# AWS使用笔记

# EC2

目前接触到的，其实就是Linux 服务器

## S3

[官方文档](https://docs.aws.amazon.com/AmazonS3/latest/userguide/Welcome.html)

[AWS SDK for Java S3 Example](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3.html)

[Full example in Github](https://github.com/awsdocs/aws-doc-sdk-examples/tree/main/java/example_code/s3/src/main/java/aws/example/s3)

对象存储工具

可以用来存储静态object

## IAM

权限管理

## RDS

关系型数据库

## Amplify

可以用来部署前后端页面

研究中 --- 

## CloudFront

[CloudFront 官方文档](https://docs.aws.amazon.com/zh_cn/AmazonCloudFront/latest/DeveloperGuide/Introduction.html)

CDN加速器



## CodePipeline

[example](https://devpress.csdn.net/cicd/62ed9babc6770329307f2966.html)

[官方方案](https://aws.amazon.com/cn/blogs/devops/complete-ci-cd-with-aws-codecommit-aws-codebuild-aws-codedeploy-and-aws-codepipeline/)

[example](https://www.cnblogs.com/CupricNitrate/p/14035951.html)

[CI/CD](https://aws.amazon.com/cn/blogs/devops/complete-ci-cd-with-aws-codecommit-aws-codebuild-aws-codedeploy-and-aws-codepipeline/)

# 部署方案

## S3 前端+ Elastic Beanstalk 后端



[Elastic Beanstalk部署](https://blog.csdn.net/danpob13624/article/details/106778329)

Elastic Beanstalk 需要配置一个点是- 它自带Nginx，因为Nginx自动监听5000端口，需要在env - properties中配置

SERVER_PORT 5000

