/*
 本脚本作用为将数据库结构及必要配置从V0.5.0之前的版本更新到V0.5.0
 参考历史版本记录：https://github.com/niefy/wx-manage/wiki/%E6%9B%B4%E6%96%B0%E8%AE%B0%E5%BD%95-versions
 使用本脚本会对数据库做以下更新，如有影响请更新前备份对应数据
    1. 更新模板消息配置（msg_template）、模板消息发送日志表（template_msg_log），相关数据会被清空
    2. 重置系统菜单配置（sys_menu）
    3. 更新微信粉丝用户表（wx_user），会清空数据（可以在管理后台再次同步）
 如使用本脚本升级后系统出现问题，请备份自己的重要数据后，执行最新的数据库脚本（重置整个数据库）

 Date: 2020/5/10
 Author: Nifury
*/

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50)  NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200)  NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500)  NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50)  NULL DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COMMENT = '菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', NULL, NULL, 0, 'el-icon-s-tools', 0), (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1), (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2), (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3), (6, 0, '微信管理', NULL, NULL, 0, 'el-icon-s-promotion', 1), (7, 0, '内容管理', '', '', 0, 'el-icon-document-copy', 2), (9, 0, '日志报表', '', '', 0, 'el-icon-s-order', 4), (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0), (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0), (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0), (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0), (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0), (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0), (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0), (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0), (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0), (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0), (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0), (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0), (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6), (29, 9, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7), (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6), (32, 6, '公众号菜单', 'wx/wx-menu', '', 1, 'log', 0), (34, 6, '素材管理', 'wx/wx-assets', NULL, 1, 'log', 0), (35, 34, '新增', '', 'wx:wxassets:save', 2, '', 0), (41, 7, '文章公告', 'wx/article', NULL, 1, 'config', 6), (42, 41, '查看', NULL, 'wx:article:list,wx:article:info', 2, NULL, 6), (43, 41, '新增', NULL, 'wx:article:save', 2, NULL, 6), (44, 41, '修改', NULL, 'wx:article:update', 2, NULL, 6), (45, 41, '删除', NULL, 'wx:article:delete', 2, NULL, 6), (66, 6, '自动回复规则', 'wx/msg-reply-rule', NULL, 1, 'config', 6), (67, 66, '查看', NULL, 'wx:msgreplyrule:list,wx:msgreplyrule:info', 2, NULL, 6), (68, 66, '新增', NULL, 'wx:msgreplyrule:save', 2, NULL, 6), (69, 66, '修改', NULL, 'wx:msgreplyrule:update', 2, NULL, 6), (70, 66, '删除', NULL, 'wx:msgreplyrule:delete', 2, NULL, 6), (71, 6, '消息模板', 'wx/msg-template', NULL, 1, 'config', 6), (72, 71, '查看', NULL, 'wx:msgtemplate:list,wx:msgtemplate:info', 2, NULL, 6), (73, 71, '新增', NULL, 'wx:msgtemplate:save', 2, NULL, 6), (74, 71, '修改', NULL, 'wx:msgtemplate:update', 2, NULL, 6), (75, 71, '删除', NULL, 'wx:msgtemplate:delete', 2, NULL, 6), (81, 9, '模版消息发送记录', 'wx/template-msg-log', NULL, 1, 'config', 6), (85, 81, '删除', NULL, 'wx:templatemsglog:delete', 2, NULL, 6), (99, 32, '更新公众号菜单', '', 'wx:menu:save', 2, '', 0), (102, 34, '查看', '', 'wx:wxassets:list', 2, '', 0), (103, 6, '带参二维码', 'wx/wx-qrcode', NULL, 1, 'config', 6), (104, 103, '查看', NULL, 'wx:wxqrcode:list,wx:wxqrcode:info', 2, NULL, 6), (105, 103, '新增', NULL, 'wx:wxqrcode:save', 2, NULL, 6), (107, 103, '删除', NULL, 'wx:wxqrcode:delete', 2, NULL, 6), (108, 6, '粉丝管理', 'wx/wx-user', NULL, 1, 'config', 6), (109, 108, '查看', NULL, 'wx:wxuser:list,wx:wxuser:info', 2, NULL, 6), (110, 108, '删除', NULL, 'wx:wxuser:delete', 2, NULL, 6), (1111, 108, '同步', NULL, 'wx:wxuser:save', 2, NULL, 6), (1112, 34, '删除', '', 'wx:wxassets:delete', 2, '', 0);
COMMIT;
