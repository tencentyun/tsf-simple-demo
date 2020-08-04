package com.tencent.cloud.task;

import com.tencent.cloud.task.sdk.client.LogReporter;
import com.tencent.cloud.task.sdk.client.model.ExecutableTaskData;
import com.tencent.cloud.task.sdk.client.model.ProcessResult;
import com.tencent.cloud.task.sdk.client.model.ProcessResultCode;
import com.tencent.cloud.task.sdk.client.model.TerminateResult;
import com.tencent.cloud.task.sdk.client.remoting.TaskExecuteFuture;
import com.tencent.cloud.task.sdk.client.spi.ExecutableTask;
import com.tencent.cloud.task.sdk.client.spi.TerminableTask;
import com.tencent.cloud.task.sdk.core.utils.ThreadUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 模拟可终止的任务
 */
public class SimpleCancelableLogTask implements ExecutableTask,TerminableTask {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private final AtomicBoolean isCancelled = new AtomicBoolean(false);

    @Override
    public TerminateResult cancel(TaskExecuteFuture future, ExecutableTaskData taskData) {
        // 设置终止状态，终止成功
        isCancelled.set(true);
        // 返回终止成功
        return TerminateResult.newTerminateSuccessResult("terminate success");

    }

    @Override
    public ProcessResult execute(ExecutableTaskData taskData) {
        ProcessResult result = new ProcessResult();
        LOG.info("start to execute task: " + this.getClass().getName());
        LogReporter.log(taskData,"start to execute task...");
        // 执行第一段业务逻辑
        process1();
        if (isCancelled.get()) {
            result.setResultCode(ProcessResultCode.TERMINATED);
            LogReporter.log(taskData,"task execute failed, caused by terminate. taskName: " + this.getClass().getName());
            // 返回终止成功
            return result;
        }
        // 执行第二段业务逻辑
        process2();
        result.setResultCode(ProcessResultCode.SUCCESS);
        LogReporter.log(taskData,"task execute success. taskName: " + this.getClass().getName());
        // 返回执行成功
        return result;
    }

    // 模拟业务执行
    private void process1() {
        long sleepTime = RandomUtils.nextLong(10000, 15000);
        ThreadUtils.waitMs(sleepTime);
    }
    // 模拟业务执行
    private void process2() {
        long sleepTime = RandomUtils.nextLong(10000, 15000);
        ThreadUtils.waitMs(sleepTime);
    }
}
