package com.tencent.cloud.task.spring;

import com.tencent.cloud.task.core.utils.ThreadUtils;
import com.tencent.cloud.task.worker.LogReporter;
import com.tencent.cloud.task.worker.model.ExecutableTaskData;
import com.tencent.cloud.task.worker.model.ProcessResult;
import com.tencent.cloud.task.worker.spi.ExecutableTask;
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
