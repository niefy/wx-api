
-- ----------------------------
-- Table structure for cms_article
-- ----------------------------
DROP TABLE IF EXISTS `cms_article`;
CREATE TABLE `cms_article`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) NULL DEFAULT 1 COMMENT '文章类型[1:普通文章,5:帮助中心]',
  `title` varchar(1024) NOT NULL COMMENT '标题',
  `summary` varchar(1024)  NULL DEFAULT NULL COMMENT '文章摘要',
  `tags` varchar(255)  NULL DEFAULT NULL COMMENT '文章标签',
  `content` longtext  NULL COMMENT '内容',
  `category` varchar(25)  NULL DEFAULT NULL COMMENT '分类',
  `sub_category` varchar(25)  NULL DEFAULT NULL COMMENT '二级目录',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `open_count` int(11) NULL DEFAULT 0 COMMENT '点击次数',
  `start_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '生效时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '失效时间',
  `target_link` varchar(255)  NULL DEFAULT NULL COMMENT '指向外链',
  `image` varchar(255)  NULL DEFAULT NULL COMMENT '文章首图',
  PRIMARY KEY (`id`)
  -- UNIQUE INDEX `idx_title`(`title`)  COMMENT '标题不得重复'
);

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`) 
) ;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) NULL DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `param_key`(`param_key`) 
)  ;


-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) NULL DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) NULL DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NULL DEFAULT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) 
) ;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) 
)  ;

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) 
) ;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) NULL DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`) 
)  ;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) 
)  ;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(100) NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`) ,
  UNIQUE INDEX `username`(`username`) 
) ;


-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) 
) ;

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) ,
  UNIQUE INDEX `token`(`token`) 
)  ;

-- ----------------------------
-- Table structure for wx_account
-- ----------------------------
DROP TABLE IF EXISTS `wx_account`;
CREATE TABLE `wx_account`  (
  `appid` char(20)  NOT NULL COMMENT 'appid',
  `name` varchar(50)  NOT NULL COMMENT '公众号名称',
  `type` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '账号类型',
  `verified` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '认证状态',
  `secret` char(32)  NOT NULL COMMENT 'appsecret',
  `token` varchar(32)  NULL DEFAULT NULL COMMENT 'token',
  `aes_key` varchar(43)  NULL DEFAULT NULL COMMENT 'aesKey',
  PRIMARY KEY (`appid`) 
)  ;

-- ----------------------------
-- Table structure for wx_msg
-- ----------------------------
DROP TABLE IF EXISTS `wx_msg`;
CREATE TABLE `wx_msg`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `appid` char(20)  NOT NULL COMMENT 'appid',
  `openid` varchar(32)  NOT NULL COMMENT '微信用户ID',
  `in_out` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '消息方向',
  `msg_type` char(25)  NULL DEFAULT NULL COMMENT '消息类型',
  `detail` json NULL COMMENT '消息详情',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`)
)  ;

-- ----------------------------
-- Table structure for wx_msg_reply_rule
-- ----------------------------
DROP TABLE IF EXISTS `msg_reply_rule`;
DROP TABLE IF EXISTS `wx_msg_reply_rule`;
CREATE TABLE `wx_msg_reply_rule`  (
  `rule_id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` char(20)  NULL DEFAULT '' COMMENT 'appid',
  `rule_name` varchar(20)  NOT NULL COMMENT '规则名称',
  `match_value` varchar(200)  NOT NULL COMMENT '匹配的关键词、事件等',
  `exact_match` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否精确匹配',
  `reply_type` varchar(20)  NOT NULL DEFAULT '1' COMMENT '回复消息类型',
  `reply_content` varchar(1024)  NOT NULL COMMENT '回复消息内容',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '规则是否有效',
  `desc` varchar(255)  NULL DEFAULT NULL COMMENT '备注说明',
  `effect_time_start` time(0) NULL DEFAULT '00:00:00' COMMENT '生效起始时间',
  `effect_time_end` time(0) NULL DEFAULT '23:59:59' COMMENT '生效结束时间',
  `priority` int(3) UNSIGNED NULL DEFAULT 0 COMMENT '规则优先级',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`rule_id`)
)  ;


-- ----------------------------
-- Table structure for wx_msg_template
-- ----------------------------
DROP TABLE IF EXISTS `msg_template`;
DROP TABLE IF EXISTS `wx_msg_template`;
CREATE TABLE `wx_msg_template`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appid` char(20)  NOT NULL COMMENT 'appid',
  `template_id` varchar(100)  NOT NULL COMMENT '公众号模板ID',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '模版名称',
  `title` varchar(20)  NULL DEFAULT NULL COMMENT '标题',
  `content` text  NULL COMMENT '模板内容',
  `data` json NULL COMMENT '消息内容',
  `url` varchar(255)  NULL DEFAULT NULL COMMENT '链接',
  `miniprogram` json NULL COMMENT '小程序信息',
  `status` tinyint(1) UNSIGNED NOT NULL COMMENT '是否有效',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`)
)  ;


-- ----------------------------
-- Table structure for wx_qr_code
-- ----------------------------
DROP TABLE IF EXISTS `wx_qr_code`;
CREATE TABLE `wx_qr_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `appid` char(20)  NOT NULL COMMENT 'appid',
  `is_temp` tinyint(1) NULL DEFAULT NULL COMMENT '是否为临时二维码',
  `scene_str` varchar(64) NULL DEFAULT NULL COMMENT '场景值ID',
  `ticket` varchar(255) NULL DEFAULT NULL COMMENT '二维码ticket',
  `url` varchar(255) NULL DEFAULT NULL COMMENT '二维码图片解析后的地址',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '该二维码失效时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '该二维码创建时间',
  PRIMARY KEY (`id`)
)  ;

-- ----------------------------
-- Table structure for wx_template_msg_log
-- ----------------------------
DROP TABLE IF EXISTS `template_msg_log`;
DROP TABLE IF EXISTS `wx_template_msg_log`;
CREATE TABLE `wx_template_msg_log`  (
  `log_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `appid` char(20)  NOT NULL COMMENT 'appid',
  `touser` varchar(50)  NULL DEFAULT NULL COMMENT '用户openid',
  `template_id` varchar(50)  NULL DEFAULT NULL COMMENT 'templateid',
  `data` json NULL COMMENT '消息数据',
  `url` varchar(255)  NULL DEFAULT NULL COMMENT '消息链接',
  `miniprogram` json NULL COMMENT '小程序信息',
  `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `send_result` varchar(255)  NULL DEFAULT NULL COMMENT '发送结果',
  PRIMARY KEY (`log_id`)
)  ;

-- ----------------------------
-- Table structure for wx_user
-- ----------------------------
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `openid` varchar(50) NOT NULL COMMENT '微信openid',
  `appid` char(20)  NOT NULL COMMENT 'appid',
  `phone` char(11) NULL DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(50) NULL DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '性别(0-未知、1-男、2-女)',
  `city` varchar(20) NULL DEFAULT NULL COMMENT '城市',
  `province` varchar(20) NULL DEFAULT NULL COMMENT '省份',
  `headimgurl` varchar(255) NULL DEFAULT NULL COMMENT '头像',
  `subscribe_time` datetime(0) NULL DEFAULT NULL COMMENT '订阅时间',
  `subscribe` tinyint(3) UNSIGNED NULL DEFAULT 1 COMMENT '是否关注',
  `unionid` varchar(50) NULL DEFAULT NULL COMMENT 'unionid',
  `remark` varchar(255) NULL DEFAULT NULL COMMENT '备注',
  `tagid_list` json NULL COMMENT '标签ID列表',
  `subscribe_scene` varchar(50) NULL DEFAULT NULL COMMENT '关注场景',
  `qr_scene_str` varchar(64) NULL DEFAULT NULL COMMENT '扫码场景值',
  PRIMARY KEY (`openid`)
)  ;