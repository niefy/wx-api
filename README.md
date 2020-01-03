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
- 项目脚手架：[renren-fast](https://gitee.com/renrenio/renren-fast)
- 页面交互：Vue2.x、ElementUI、TinyMce Editor、

# 后端部署
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库wx，数据库编码为UTF-8
- 执行db/mysql.sql文件，初始化数据
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行BootApplication.java，则可启动项目

# 管理后台前端部署

- 本项目是前后端分离的，还需要部署前端，才能运行起来
- 前端下载地址：https://github.com/niefy/wx-manage
- 前端部署完毕，就可以访问项目了，账号：admin，密码：admin

# 用户界面前端部署
- 本项目是前后端分离的，还需要部署前端，才能运行起来
- 前端下载地址：https://github.com/niefy/wx-client