# wx-api
微信公众号管理项目，基于Spring Boot2.1快速公众号开发种子项目，包含公众号菜单管理、自动回复、素材管理等

### [☁项目前端](https://github.com/niefy/wx-manage) | [📖使用文档](https://github.com/niefy/wx-manage/wiki) | [📊功能投票](https://wj.qq.com/s2/5896407/a381) | [🕓版本更新记录](https://github.com/niefy/wx-manage/wiki/更新记录-versions) | [Github仓库](https://github.com/niefy/wx-api) | [码云仓库](https://gitee.com/niefy/wx-api)

## 项目说明
- wx-api是一个轻量级的公众号开发种子项目，可快速接入微信公众号管理功能
- 管理后台前端项目wx-manage：https://github.com/niefy/wx-manage
- 微信登录、微信分享、CMS前端示例：https://github.com/niefy/wx-client

## 技术选型
- 核心框架：Spring Boot
- 安全框架：Apache Shiro
- 持久层框架：[MyBatis-Plus](https://baomidou.oschina.io/mybatis-plus-doc/#/quick-start)
- 公众号开发框架：[WxJava](https://github.com/Wechat-Group/WxJava)
- 项目脚手架：[renren-fast](https://gitee.com/renrenio/renren-fast)
- 页面交互：Vue2.x、ElementUI、TinyMce Editor、Vuex


## 公众号开发准备
- 微信公众号启用开发模式（开发测试可使用[微信公众号测试号](https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login)）
- 后端服务必需配置域名和80端口（开发测试可使用[内网穿透工具ngrok](https://blog.csdn.net/chain_fei/article/details/79152692)）

## 开发环境部署
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

## 管理后台前端部署
- 本项目是前后端分离的，还需要部署前端，才能运行起来
- 前端下载地址：https://github.com/niefy/wx-manage
- 前端部署完毕，就可以访问项目了，账号：admin，密码：123456

## [生产环境部署步骤](https://github.com/niefy/wx-manage/wiki/生产环境部署文档)

## 截图
![公众号菜单](https://s1.ax1x.com/2020/04/10/GTq6sI.png)
![自动回复](https://s1.ax1x.com/2020/04/10/GTqyQA.png)
![模板消息配置](https://s1.ax1x.com/2020/04/18/JnKZhF.jpg)
![模板消息发送](https://s1.ax1x.com/2020/04/18/JnKEkT.jpg)
![粉丝管理](https://s1.ax1x.com/2020/04/18/JnKVtU.jpg)
![带参二维码](https://s1.ax1x.com/2020/04/18/JnKF00.jpg)
![素材管理](https://s1.ax1x.com/2020/05/20/Y7djHI.jpg)
![公众号消息](https://s1.ax1x.com/2020/05/20/Y7dXDA.jpg)
![文章编辑](https://s1.ax1x.com/2020/04/10/GTqrzd.png)
![系统菜单管理](https://s1.ax1x.com/2020/04/18/JnKk7V.jpg)
![管理员列表](https://s1.ax1x.com/2020/04/18/JnKimq.jpg)

## 开发进度
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
    - [x] 图片、语音、视频、图文素材管理
    - [x] 支持需要素材ID时从素材库选择
- [X] 公众号粉丝管理
    - [x] 同步粉丝列表
    - [x] 关注/取关信息更新
    - [x] 标签管理
    - [x] 用户标签调整
- [X] 模板消息
    - [x] 提供demo（正常需根据业务场景去实现）
    - [x] 按用户标签推送自定义模板消息
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
- [x] 消息管理
    - [x] 消息、事件列表
    - [x] 消息回复
- [ ] 微信卡券
- [ ] 客服管理

## 代码贡献指南
1. 首先非常欢迎和感谢对本项目发起Pull Request的同学。
1. **特别提示：请务必在dev分支提交PR，master分支目前仅是正式版的代码。**
1. 本项目代码风格为使用4个空格代表一个Tab，因此在提交代码时请注意一下，否则很容易在IDE格式化代码后与原代码产生大量diff，这样会给其他人阅读代码带来极大的困扰。
1. 为了便于设置，本项目引入editorconfig支持，请使用Eclipse的同学在贡献代码前安装相关插件，而IntelliJ IDEA新版本自带支持，如果没有可自行安装插件。
1. 本项目贡献代码方式：
>* 在 GitHub 上 `fork` 到自己的仓库，如 `my_user/wx-api`，然后 `clone` 到本地，并设置用户信息。
>* 修改代码后提交，并推送到自己的仓库。
>* 在 GitHub 网站上提交 Pull Request。

## 开发交流
QQ群：1023785886（技术交流群严禁广告，发广告立即踢出+拉黑+举报） 加群密码：wx 
