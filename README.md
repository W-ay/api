# API接口开放平台
## 介绍
接口开放平台，管理员可以管理所有接口，统计用户接口调用次数。
用户可以注册账户，在开通接口并购买接口调用次数后，可以在线调用接口进行测试，也可根据自己的accessKey、secretKey在自己的项目中调用接口

### 模块介绍
1. backend：提供用户注册、登录，接口开通、删除、增加调用次数、统计调用信息等一系列后端功能
2. api-gateway：API 同一访问地址，在这里进行接口访问鉴权、调用次数记录等
3. api-interface：API 实际实现模块
4. api-client-sdk：提供给开发者，用于在自建项目中快速访问API
5. dubbo-interface：公共模块，用于存放所有项目通用的类

## 使用
1. 项目所需依赖
    1. 必须
        1. JDK 8
        2. MySQL 5.7
        3. Nacos 2.2.1
    2. 可选
        1. Redis
2. 克隆仓库至本地
3. 运行sql/script.sql 构建数据表
4. 修改所有application-dev.yml内的配置信息为自己本地信息
5. 先运行backend项目
6. backend启动成功后运行gateway、api-interface

## 其他
1. 日志
   日志用logback实现，项目日志位于项目根目录的logs文件夹里，方便上线后查错
2. assets目录
    1. avatar-urls.txt 里是我存到图库的图片的地址
    2. poem.csv 是随机古诗接口要用到的古诗信息，若要添加古诗，修改此文件内容
3. Knife4j地址： http://localhost:17529/api/doc.html 就能在线调试接口了，不需要前端配合


## 反馈
如果您在运行中出现问题，请在issue中留言
