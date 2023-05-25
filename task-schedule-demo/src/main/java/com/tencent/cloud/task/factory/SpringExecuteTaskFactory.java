package com.tencent.cloud.task.factory;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.tencent.cloud.task.sdk.client.DefaultTaskFactory;
import com.tencent.cloud.task.sdk.client.exception.InstancingException;
import com.tencent.cloud.task.sdk.client.model.ExecutableTaskData;
import com.tencent.cloud.task.sdk.client.spi.ExecutableTask;
import com.tencent.cloud.task.sdk.client.spi.ExecutableTaskFactory;

@Component
public class SpringExecuteTaskFactory implements ExecutableTaskFactory, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ApplicationContext applicationContext;

    private final ExecutableTaskFactory defaultFactory = new DefaultTaskFactory(Thread.currentThread().getContextClassLoader());

    @Override
    public ExecutableTask newExecutableTask(ExecutableTaskData executableTaskData) throws InstancingException {
        try {
            ExecutableTask executableTask = (ExecutableTask) applicationContext.getBean(Class.forName(executableTaskData.getTaskContent()));
            LOG.info("generate executableTask bean SpringExecutableTaskFactory. taskName: {}", executableTaskData.getTaskContent());
            return executableTask;
        } catch (Throwable t) {
            LOG.warn("executableTask bean not found, try default factory. taskName: {}", executableTaskData.getTaskContent());
            return defaultFactory.newExecutableTask(executableTaskData);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
