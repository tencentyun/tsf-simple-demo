package com.tencent.cloud.task;

import com.tencent.cloud.task.sdk.client.LogReporter;
import com.tencent.cloud.task.sdk.client.model.ExecutableTaskData;
import com.tencent.cloud.task.sdk.client.model.ProcessResult;
import com.tencent.cloud.task.sdk.client.model.ProcessResultCode;
import com.tencent.cloud.task.sdk.client.spi.ExecutableTask;
import com.tencent.cloud.task.sdk.core.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * 模拟重试类型的任务。
 */
public class SimpleRetryExecuteTask implements ExecutableTask {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public ProcessResult execute(ExecutableTaskData taskData) {
        ProcessResult result = new ProcessResult();
        try {
            //
            String startMsg = "retry task start, taskName: " + SimpleRetryExecuteTask.class.getName() + ", retryTime: "
                    + taskData.getRetryTime();
            // 添加日志，将在控制台执行详情日志中显示。
            LogReporter.log(taskData,startMsg);
            LogReporter.log(taskData,"hello, this is a demo for retry task");
            // 模拟耗时
            ThreadUtils.waitMs(3000);
            long seed = CounterUtil.getNextCount();
            if (seed % 3 == 0 || seed % 4 == 0) {
                LOG.info("return retry task failed, seed: {}", seed);
                LogReporter.log(taskData,String.format("task execute failed, retry task failed, seed: %d", seed));
                result.setResultCode(ProcessResultCode.FAIL);
                return result;
            } else {
                LogReporter.log(taskData,"task execute success, seed: " + seed);
                result.setResultCode(ProcessResultCode.SUCCESS);
            }
        } catch (Throwable t) {
            result.setResultCode(ProcessResultCode.FAIL);
            LogReporter.log(taskData,t.getMessage());
        }
        return result;
    }
}
