package com.tencent.cloud.task.spring;

import com.tencent.cloud.task.sdk.client.LogReporter;
import com.tencent.cloud.task.sdk.client.model.ExecutableTaskData;
import com.tencent.cloud.task.sdk.client.model.ProcessResult;
import com.tencent.cloud.task.sdk.client.spi.ExecutableTask;
import com.tencent.cloud.task.sdk.core.utils.ThreadUtils;
import org.springframework.stereotype.Component;

@Component
public class SimpleSpringBeanLogTask implements ExecutableTask {
    @Override
    public ProcessResult execute(ExecutableTaskData executableTaskData) {
        LogReporter.log(executableTaskData,"spring bean task start to execute");
        ThreadUtils.waitMs(5000);
        LogReporter.log(executableTaskData,"spring bean task end to execute");
        return ProcessResult.newSuccessResult();
    }
}
