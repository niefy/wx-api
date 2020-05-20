
-- ----------------------------
-- 消息回复规则添加字段
-- ----------------------------
ALTER TABLE `msg_reply_rule`
ADD COLUMN `priority` int(3) UNSIGNED NULL DEFAULT 0 COMMENT '规则优先级',
ADD COLUMN `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间';

-- ----------------------------
-- Table structure for wx_msg
-- ----------------------------
DROP TABLE IF EXISTS `wx_msg`;
CREATE TABLE `wx_msg`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '微信用户ID',
  `in_out` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '消息方向',
  `msg_type` char(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '消息类型',
  `detail` json NULL COMMENT '消息详情',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '微信消息';

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (113, 6, '微信消息', 'wx/wx-msg', NULL, 1, 'config', 6);
INSERT INTO `sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (114, 113, '查看', NULL, 'wx:wxmsg:list,wx:wxmsg:info', 2, NULL, 6);
INSERT INTO `sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (115, 113, '新增', NULL, 'wx:wxmsg:save', 2, NULL, 6);
INSERT INTO `sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES (117, 113, '删除', NULL, 'wx:wxmsg:delete', 2, NULL, 6);
