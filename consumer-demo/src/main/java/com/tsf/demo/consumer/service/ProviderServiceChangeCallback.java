/*
 * Tencent is pleased to support the open source community by making Spring Cloud Tencent available.
 *
 * Copyright (C) 2019 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tsf.demo.consumer.service;

import com.tencent.cloud.polaris.discovery.refresh.ServiceInstanceChangeCallback;
import com.tencent.cloud.polaris.discovery.refresh.ServiceInstanceChangeListener;
import com.tencent.polaris.api.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Call back for provider-demo.
 *
 * @author Haotian Zhang
 */
@Component
@ServiceInstanceChangeListener(serviceName = "provider-demo")
public class ProviderServiceChangeCallback implements ServiceInstanceChangeCallback {

    private static final Logger LOG = LoggerFactory.getLogger(ProviderServiceChangeCallback.class);

    @Override
    public void callback(List<Instance> currentServiceInstances, List<Instance> addServiceInstances, List<Instance> deleteServiceInstances) {
        String current = generateNodeList(currentServiceInstances);
        String add = generateNodeList(addServiceInstances);
        String delete = generateNodeList(deleteServiceInstances);
        LOG.info("current: {}, add: {}, delete: {}", current, add, delete);
    }

    private String generateNodeList(List<Instance> deleteServiceInstances) {
        StringBuilder nodeListStr = new StringBuilder("[");
        for (Instance instance : deleteServiceInstances) {
            if (nodeListStr.length() > 1) {
                nodeListStr.append(", ");
            }
            nodeListStr.append(instance.getHost()).append(":").append(instance.getPort());
        }
        nodeListStr.append("]");
        return nodeListStr.toString();
    }
}
