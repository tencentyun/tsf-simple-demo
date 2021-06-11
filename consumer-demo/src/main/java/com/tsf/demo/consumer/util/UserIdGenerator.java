/*
 * Copyright (c) 2020 www.tencent.com.
 * All Rights Reserved.
 * This program is the confidential and proprietary information of
 * www.tencent.com ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with www.tencent.com.
 */   
package com.tsf.demo.consumer.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.tencent.tsf.unit.util.TsfUnitRuleGenerator;


public class UserIdGenerator implements TsfUnitRuleGenerator {

    /** 
     * 
     * @see com.tencent.tsf.unit.util.TsfUnitRuleGenerator#generateRule(java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Map<String, String> generateRule(Method method, Object[] args) {
        String userId = (String)args[0];
        Map<String, String> ruleMap = new HashMap<>();
        // 添加单元化规则的tag key
        ruleMap.put("userId", userId);
        return ruleMap;
    }

}
  