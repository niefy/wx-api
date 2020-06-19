package com.github.niefy.modules.wx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.niefy.modules.wx.service.MsgReplyRuleService;
import com.github.niefy.common.utils.PageUtils;
import com.github.niefy.common.utils.Query;
import com.github.niefy.modules.wx.entity.MsgReplyRule;
import com.github.niefy.modules.wx.dao.MsgReplyRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MsgReplyRuleServiceImpl extends ServiceImpl<MsgReplyRuleMapper, MsgReplyRule> implements MsgReplyRuleService {
    @Autowired
    MsgReplyRuleMapper msgReplyRuleMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String matchValue = (String) params.get("matchValue");
        String appid = (String) params.get("appid");
        IPage<MsgReplyRule> page = this.page(
            new Query<MsgReplyRule>().getPage(params),
            new QueryWrapper<MsgReplyRule>()
                    .eq(!StringUtils.isEmpty(appid), "appid", appid)
                    .or()
                    .apply("appid is null or appid = ''")
                    .like(!StringUtils.isEmpty(matchValue), "match_value", matchValue)
                    .orderByDesc("update_time")
        );

        return new PageUtils(page);
    }

    /**
     * 保存自动回复规则
     *
     * @param msgReplyRule
     */

    @Override
    public boolean save(MsgReplyRule msgReplyRule) {
        if (msgReplyRule.getRuleId() > 0) {
            msgReplyRuleMapper.updateById(msgReplyRule);
        } else {
            msgReplyRuleMapper.insert(msgReplyRule);
        }
        return false;
    }

    /**
     * 获取所有的回复规则
     *
     * @return
     */
    @Override
    public List<MsgReplyRule> getRules() {
        return msgReplyRuleMapper.selectList(new QueryWrapper<MsgReplyRule>().orderByDesc("rule_id"));
    }

    /**
     * 获取当前时段内所有有效的回复规则
     *
     * @return
     */
    @Override
    public List<MsgReplyRule> getValidRules() {
        return msgReplyRuleMapper.selectList(
            new QueryWrapper<MsgReplyRule>()
                .eq("status", 1)
                .isNotNull("match_value")
                .ne("match_value", "")
                .orderByDesc("priority"));
    }

    /**
     * 筛选符合条件的回复规则
     *
     *
     * @param appid 公众号appid
     * @param exactMatch 是否精确匹配
     * @param keywords   关键词
     * @return 规则列表
     */
    @Override
    public List<MsgReplyRule> getMatchedRules(String appid, boolean exactMatch, String keywords) {
        LocalTime now = LocalTime.now();
        return this.getValidRules().stream()
                .filter(rule->StringUtils.isEmpty(rule.getAppid()) || appid.equals(rule.getAppid())) // 检测是否是对应公众号的规则，如果appid为空则为通用规则
                .filter(rule->null == rule.getEffectTimeStart() || rule.getEffectTimeStart().toLocalTime().isBefore(now))// 检测是否在有效时段，effectTimeStart为null则一直有效
                .filter(rule->null == rule.getEffectTimeEnd() || rule.getEffectTimeEnd().toLocalTime().isAfter(now)) // 检测是否在有效时段，effectTimeEnd为null则一直有效
                .filter(rule->isMatch(exactMatch || rule.isExactMatch(),rule.getMatchValue().split(","),keywords)) //检测是否符合匹配规则
                .collect(Collectors.toList());
    }

    /**
     * 检测文字是否匹配规则
     * 精确匹配时，需关键词与规则词语一致
     * 非精确匹配时，检测文字需包含任意一个规则词语
     *
     * @param exactMatch 是否精确匹配
     * @param ruleWords  规则列表
     * @param checkWords 需检测的文字
     * @return
     */
    public static boolean isMatch(boolean exactMatch, String[] ruleWords, String checkWords) {
        if (StringUtils.isEmpty(checkWords)) return false;
        for (String words : ruleWords) {
            if (exactMatch && words.equals(checkWords)) return true;//精确匹配，需关键词与规则词语一致
            if (!exactMatch && checkWords.contains(words)) return true;//模糊匹配
        }
        return false;
    }
}
