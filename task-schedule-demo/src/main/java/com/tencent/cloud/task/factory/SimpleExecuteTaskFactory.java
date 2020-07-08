package com.tencent.cloud.task.factory;

import com.tencent.cloud.task.worker.DefaultTaskFactory;
import com.tencent.cloud.task.worker.exception.InstancingException;
import com.tencent.cloud.task.worker.model.ExecutableTaskData;
import com.tencent.cloud.task.worker.spi.ExecutableTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class SimpleExecuteTaskFactory extends DefaultTaskFactory {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public SimpleExecuteTaskFactory() {
        super(Thread.currentThread().getContextClassLoader());
    }

    public SimpleExecuteTaskFactory(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public ExecutableTask newExecutableTask(ExecutableTaskData taskData) throws InstancingException {
        LOG.info("generate task: {}", taskData.getTaskContent());
        return super.newExecutableTask(taskData);
    }
}
