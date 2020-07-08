package com.tencent.cloud.task;

import com.tencent.cloud.task.worker.model.ExecutableTaskData;
import com.tencent.cloud.task.worker.model.ProcessResult;
import com.tencent.cloud.task.worker.model.TerminateResult;
import com.tencent.cloud.task.worker.remoting.TaskExecuteFuture;
import com.tencent.cloud.task.worker.spi.ExecutableTask;
import com.tencent.cloud.task.worker.spi.TerminableTask;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * 模拟超时任务, 执行之间在 30s ~ 40s 之间, 可以终止。
 */
public class SimpleTimeoutExecutableTask implements ExecutableTask, TerminableTask {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Override
    public ProcessResult execute(ExecutableTaskData taskData) {
        try {
            long startTime = System.currentTimeMillis();
            long timeOut = RandomUtils.nextLong(30000L, 40000L);
            LOG.info("timeout task start, timeout: {}",timeOut);
            Thread.sleep(timeOut);
            LOG.info("timeout task end, span: {}", System.currentTimeMillis() - startTime);
            return ProcessResult.newSuccessResult();
        } catch (InterruptedException e) {
            return ProcessResult.newCancelledResult(e);
        }
    }

    @Override
    public TerminateResult cancel(TaskExecuteFuture future, ExecutableTaskData taskData) {
        future.cancel(true);
        return TerminateResult.newTerminateSuccessResult("success to interrupted");
    }
}
