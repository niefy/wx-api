package com.github.niefy.modules.wx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.modules.wx.entity.MsgReplyRule;

import java.util.List;
import java.util.Map;

public interface MsgReplyRuleService extends IService<MsgReplyRule> {
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存自动回复规则
     *
     * @param msgReplyRule
     */

    boolean save(MsgReplyRule msgReplyRule);

    /**
     * 获取所有的回复规则
     *
     * @return
     */
    List<MsgReplyRule> getRules();

    /**
     * 获取当前时段内所有有效的回复规则
     *
     * @return 有效的规则列表
     */
    List<MsgReplyRule> getValidRules();

    /**
     * 筛选符合条件的回复规则
     *
     * @param exactMatch 是否精确匹配
     * @param keywords   关键词
     * @return 规则列表
     */
    List<MsgReplyRule> getMatchedRules(boolean exactMatch, String keywords);
}
