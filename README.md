# wx-api
微信公众号管理项目，基于Spring Boot2.1快速公众号开发种子项目，包含公众号菜单管理、自动回复、素材管理等

# 项目说明
- wx-api是一个轻量级的公众号开发种子项目，可快速接入微信公众号管理功能
- 管理后台前端项目wx-manage：https://github.com/niefy/wx-manage
- 微信登录、微信分享、CMS前端示例：https://github.com/niefy/wx-client

# 技术选型
- 核心框架：Spring Boot
- 安全框架：Apache Shiro
- 持久层框架：[MyBatis-Plus](https://baomidou.oschina.io/mybatis-plus-doc/#/quick-start)
- 公众号开发框架：[WxJava](https://github.com/Wechat-Group/WxJava)
- 项目脚手架：[renren-fast](https://gitee.com/renrenio/renren-fast)
- 页面交互：Vue2.x、ElementUI、TinyMce Editor、Vuex


# 公众号开发准备
- 微信公众号启用开发模式（开发测试可使用[微信公众号测试号](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)）
- 后端服务必需配置域名和80端口（开发测试可使用[内网穿透工具ngrok](https://blog.csdn.net/chain_fei/article/details/79152692)）

# 后端部署
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库wx，数据库字符集utf8mb4，编码规则utf8mb4_general_ci（如未正确设置可能影响昵称表情及特殊符号存储）
- 执行db/mysql.sql文件，初始化数据，需要Mysql版本5.7+
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


# 开发进度
- [x] 公众号菜单管理
    - [x] 与微信端公众号一致的可视化界面
    - [x] 支持配置链接、点击事件、小程序等多种菜单
- [x] 公众号自动回复（使用更灵活的客服消息接口做自动回复）
    - [x] 支持配置关注事件、扫描带参二维码、接受消息等场景自动回复
    - [x] 支持一个事件或一条消息进行多次回复
    - [x] 精确匹配、模糊匹配、多关键词、可配置时段
    - [x] 文字回复
    - [x] 图片、语音、视频、图文素材等媒体形式回复
    - [x] 回复小程序卡片、回复菜单消息
- [x] 公众号带参二维码管理
    - [x] 可配置临时带参二维码、永久带参二维码
- [x] 公众号素材管理
    - [x] 上传图片、语音、视频素材,获取素材media_id
    - [x] 获取公众号图文素材
    - [ ] 添加公众号图文素材
- [X] 公众号用户管理
- [X] 模板消息(提供demo,正常需根据业务场景去实现)
- [x] 后台权限管理
    - [x] 可配置权限
    - [x] 动态后台菜单
    - [x] 数据字典
    - [x] 腾讯云、阿里云、七牛云对象存储
- [x] CMS文章
    - [x] 后台富文本文章编辑
    - [x] 微信端文章展示
- [x] 微信端
    - [x] 微信授权登录
    - [x] 微信分享
- [ ] 微信卡券
- [ ] 客服管理

## 开发交流
QQ群：1023785886 进群密码：wx
