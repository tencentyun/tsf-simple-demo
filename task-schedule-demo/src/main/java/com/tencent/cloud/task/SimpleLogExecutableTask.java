package com.tencent.cloud.task;

import com.tencent.cloud.task.sdk.client.LogReporter;
import com.tencent.cloud.task.sdk.client.model.ExecutableTaskData;
import com.tencent.cloud.task.sdk.client.model.ProcessResult;
import com.tencent.cloud.task.sdk.client.model.ProcessResultCode;
import com.tencent.cloud.task.sdk.client.model.TerminateResult;
import com.tencent.cloud.task.sdk.client.remoting.TaskExecuteFuture;
import com.tencent.cloud.task.sdk.client.spi.ExecutableTask;
import com.tencent.cloud.task.sdk.client.spi.TerminableTask;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * 模拟简单的任务, 执行时间在10s ~ 15s之间, 可以终止。
 */
public class SimpleLogExecutableTask implements ExecutableTask,TerminableTask {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public ProcessResult execute(ExecutableTaskData taskData) {
        ProcessResult result = new ProcessResult();
        try {
            LogReporter.log(taskData,"start to execute SimpleLogExecutableTask...");
            LogReporter.log(taskData,"hello, this is a demo for SimpleLogExecutableTask");
            long sleepTime = RandomUtils.nextLong(10000, 15000);
            //ThreadUtils.waitMs(sleepTime);
            Thread.sleep(sleepTime);
            result.setResultCode(ProcessResultCode.SUCCESS);
            LogReporter.log(taskData,"success to execute SimpleLogExecutableTask... ");
        } catch (InterruptedException e) {
            result.setResultCode(ProcessResultCode.TERMINATED);
            LogReporter.log(taskData,"task is terminated... ");
        } catch (Throwable t) {
            LOG.error(t.getMessage(), t);
            result.setResultCode(ProcessResultCode.FAIL);
        }
        return  result;
    }

    @Override
    public TerminateResult cancel(TaskExecuteFuture future, ExecutableTaskData taskData) {
        LogReporter.log(taskData,"task start to cancel");
        future.cancel(true);
        LogReporter.log(taskData,"task cancel success");
        return TerminateResult.newTerminateSuccessResult();
    }
}
