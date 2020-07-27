package com.tencent.cloud.task;

import com.tencent.cloud.task.sdk.client.LogReporter;
import com.tencent.cloud.task.sdk.client.model.ExecutableTaskData;
import com.tencent.cloud.task.sdk.client.model.ProcessResult;
import com.tencent.cloud.task.sdk.client.model.ProcessResultCode;
import com.tencent.cloud.task.sdk.client.spi.ExecutableTask;
import com.tencent.cloud.task.sdk.core.utils.ThreadUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * 模拟不可终止的任务
 */
public class SimpleUnCancelableTask implements ExecutableTask {
    @Override
    public ProcessResult execute(ExecutableTaskData taskData) {

        ProcessResult result = new ProcessResult();
        try {
            LogReporter.log(taskData, "start to execute SimpleUnCancelableTask...");
            LogReporter.log(taskData, "hello, this is a demo for SimpleUnCancelableTask");
            long sleepTime = RandomUtils.nextLong(10000, 15000);
            long startTime = System.currentTimeMillis();
            // 模拟当前任务耗时
            for (; ; ) {
                long time = System.currentTimeMillis() - startTime;
                if (time < sleepTime) {
                    ThreadUtils.waitMs(sleepTime - time);
                } else {
                    break;
                }
            }
            result.setResultCode(ProcessResultCode.SUCCESS);
            LogReporter.log(taskData, "success to execute SimpleUnCancelableTask... ");
        } catch (Throwable t) {
            LogReporter.log(taskData, t.getMessage());
            result.setResultCode(ProcessResultCode.FAIL);
        }
        return result;
    }
}
