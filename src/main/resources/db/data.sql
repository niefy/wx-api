-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"type\":3,\"qiniuDomain\":\"\",\"qiniuPrefix\":\"\",\"qiniuAccessKey\":\"\",\"qiniuSecretKey\":\"\",\"qiniuBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunPrefix\":\"\",\"aliyunEndPoint\":\"\",\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudAppId\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qcloudBucketName\":\"\",\"qcloudRegion\":\"ap-guangzhou\"}', 0, '云存储配置信息');


-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'el-icon-s-tools', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` VALUES (6, 0, '微信管理', NULL, NULL, 0, 'el-icon-s-promotion', 1);
INSERT INTO `sys_menu` VALUES (7, 0, '内容管理', '', '', 0, 'el-icon-document-copy', 2);
INSERT INTO `sys_menu` VALUES (9, 0, '日志报表', '', '', 0, 'el-icon-s-order', 4);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (29, 9, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);
INSERT INTO `sys_menu` VALUES (32, 6, '公众号菜单', 'wx/wx-menu', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (33, 6, '素材管理', 'wx/wx-assets', '', 1, '', 0);
INSERT INTO `sys_menu` VALUES (41, 7, '文章管理', 'wx/article', NULL, 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (42, 41, '查看', NULL, 'wx:article:list,wx:article:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (43, 41, '新增', NULL, 'wx:article:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (44, 41, '修改', NULL, 'wx:article:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (45, 41, '删除', NULL, 'wx:article:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (66, 6, '自动回复规则', 'wx/msg-reply-rule', NULL, 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (67, 66, '查看', NULL, 'wx:msgreplyrule:list,wx:msgreplyrule:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (68, 66, '新增', NULL, 'wx:msgreplyrule:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (69, 66, '修改', NULL, 'wx:msgreplyrule:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (70, 66, '删除', NULL, 'wx:msgreplyrule:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (71, 6, '模板消息', 'wx/msg-template', NULL, 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (72, 71, '查看', NULL, 'wx:msgtemplate:list,wx:msgtemplate:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (73, 71, '新增', NULL, 'wx:msgtemplate:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (74, 71, '修改', NULL, 'wx:msgtemplate:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (75, 71, '删除', NULL, 'wx:msgtemplate:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (81, 9, '模版消息发送记录', 'wx/template-msg-log', NULL, 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (84, 81, '列表', NULL, 'wx:templatemsglog:list', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (85, 81, '删除', NULL, 'wx:templatemsglog:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (99, 32, '更新公众号菜单', '', 'wx:menu:save', 2, '', 0);
INSERT INTO `sys_menu` VALUES (100, 33, '查看', '', 'wx:wxassets:list', 2, '', 0);
INSERT INTO `sys_menu` VALUES (101, 33, '新增修改', '', 'wx:wxassets:save', 2, '', 0);
INSERT INTO `sys_menu` VALUES (103, 6, '带参二维码', 'wx/wx-qrcode', NULL, 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (104, 103, '查看', NULL, 'wx:wxqrcode:list,wx:wxqrcode:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (105, 103, '新增', NULL, 'wx:wxqrcode:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (107, 103, '删除', NULL, 'wx:wxqrcode:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (108, 6, '粉丝管理', 'wx/wx-user', NULL, 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (109, 108, '查看', NULL, 'wx:wxuser:list,wx:wxuser:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (110, 108, '删除', NULL, 'wx:wxuser:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (111, 108, '同步', '', 'wx:wxuser:save', 2, '', 6);
INSERT INTO `sys_menu` VALUES (112, 33, '删除', '', 'wx:wxassets:delete', 2, '', 0);
INSERT INTO `sys_menu` VALUES (113, 6, '公众号消息', 'wx/wx-msg', NULL, 1, '', 6);
INSERT INTO `sys_menu` VALUES (114, 113, '查看', NULL, 'wx:wxmsg:list,wx:wxmsg:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (115, 113, '新增', NULL, 'wx:wxmsg:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (117, 113, '删除', NULL, 'wx:wxmsg:delete', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (118, 6, '公众号账号', 'wx/wx-account', NULL, 1, 'config', 6);
INSERT INTO `sys_menu` VALUES (119, 118, '查看', NULL, 'wx:wxaccount:list,wx:wxaccount:info', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (120, 118, '新增', NULL, 'wx:wxaccount:save', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (121, 118, '修改', NULL, 'wx:wxaccount:update', 2, NULL, 6);
INSERT INTO `sys_menu` VALUES (122, 118, '删除', NULL, 'wx:wxaccount:delete', 2, NULL, 6);

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'cdac762d0ba79875489f6a8b430fa8b5dfe0cdd81da38b80f02f33328af7fd4a', 'YzcmCZNvbXocrsz9dm8e', 'niefy@qq.com', '16666666666', 1, 1, '2016-11-11 11:11:11');

-- ----------------------------
-- Records of wx_msg_reply_rule
-- ----------------------------
INSERT INTO `wx_msg_reply_rule` VALUES (1, '', '关注公众号', 'subscribe', 0, 'text', '你好，欢迎关注！\n<a href=\"https://github.com/niefy\">点击链接查看我的主页</a>', 1, '关注回复', '00:00:00', '23:59:59', 0, '2020-05-20 15:15:00');
