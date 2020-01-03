# wx-api
微信公众号管理项目，基于Spring Boot2.1快速公众号开发种子项目，包含公众号菜单管理、自动回复、素材管理等

# 项目说明
- wx-api是一个轻量级的公众号开发种子项目，可快速接入微信公众号管理功能
- 管理后台前端项目wx-manage：https://github.com/niefy/wx-manage
- 微信登录、微信分享、CMS前端示例：https://github.com/niefy/wx-client

# 技术选型
- 核心框架：Spring Boot 2.1
- 安全框架：Apache Shiro 1.4
- 持久层框架：MyBatis-Plus
- 公众号开发框架：[WxJava](https://github.com/Wechat-Group/WxJava)
- 项目脚手架：[renren-fast](https://gitee.com/renrenio/renren-fast)
- 页面交互：Vue2.x、ElementUI、TinyMce Editor、Vuex

# 公众号开发准备
- 微信公众号启用开发模式（开发测试可使用[微信公众号测试号](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)）
- 后端服务必需配置域名和80端口（开发测试可使用[内网穿透工具ngrok](https://blog.csdn.net/chain_fei/article/details/79152692)）

# 后端部署
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库wx，数据库编码为UTF-8
- 执行db/mysql.sql文件，初始化数据
- Eclipse、IDEA打开项目，等待maven自动引入依赖
- 修改application-dev.yml，更新MySQL账号和密码
- 修改application-dev.yml，更改微信公众号appid、secret、token，开发时建议申请[微信公众号测试号](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)
- 运行BootApplication.java，则可启动项目
- 如开发环境无公网域名，需使用[ngrok](https://ngrok.com/)、[花生壳](https://hsk.oray.com/)等开启反向代理，映射:localhost:8088到外网80端口
    ``` bash
    ngrok http  8088
    // 结果示例 Forwarding：http://xxx.ngrok.io -> http://localhost:8088
    // 表示可以通过http://xxx.ngrok.io访问到开发机器8088端口
    ```
- 进入公众号后台填写公众号开发配置（如配置失败请检查反向代理是否配置正确）
    - url：http://xxx.ngrok.io/wx/wx/msg
    - Token：必须与application-dev.yml配置文件中的Token一致，可以为任意字符串
- 进入公众号后台填写公众号JS接口安全域名（如未配置会受影响微信分享）
    - http://xxx.ngrok.io
- 进入公众号后台填写授权回调页面域名（如未配置会受影响微信登录）
    - http://xxx.ngrok.io

# 管理后台前端部署
- 本项目是前后端分离的，还需要部署前端，才能运行起来
- 前端下载地址：https://github.com/niefy/wx-manage
- 前端部署完毕，就可以访问项目了，账号：admin，密码：admin

# 用户界面前端部署
- 本项目是前后端分离的，还需要部署前端，才能运行起来
- 前端下载地址：https://github.com/niefy/wx-client

## 截图
![公众号菜单](https://raw.githubusercontent.com/niefy/wx-manage/master/screenshoot/菜单管理.png)
![带参二维码](https://raw.githubusercontent.com/niefy/wx-manage/master/screenshoot/带参二维码.png)
![文章编辑](https://raw.githubusercontent.com/niefy/wx-manage/master/screenshoot/文章编辑.png)
![自动回复](https://raw.githubusercontent.com/niefy/wx-manage/master/screenshoot/自动回复.png)

## 开发交流
QQ群：732633965
进群密码：wx