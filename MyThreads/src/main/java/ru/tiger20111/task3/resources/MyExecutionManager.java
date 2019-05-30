package ru.tiger20111.task3.resources;

import ru.tiger20111.task1.resources.FixedThreadPool;
import ru.tiger20111.task3.interfaces.Context;
import ru.tiger20111.task3.interfaces.ExecutionManager;

import static java.lang.Thread.yield;


public class MyExecutionManager implements ExecutionManager {

  private final FixedThreadPool threadPool;

  public MyExecutionManager(FixedThreadPool threadPool) {

    this.threadPool = threadPool;
    threadPool.start();
  }

  @Override
  public Context execute(Runnable callback, Runnable... tasks) {

    new Thread(() -> {
      for (Runnable task : tasks) {
        threadPool.execute(task);
      }

      while (threadPool.getCompletedTasks() + threadPool.getFailedTasks() < tasks.length) {
        yield();
      }

      threadPool.execute(callback);
    }).start();

    return new Context() {
      @Override
      public int getCompletedTaskCount() {
        return threadPool.getCompletedTasks();
      }

      @Override
      public int getFailedTaskCount() {
        return threadPool.getFailedTasks();
      }

      @Override
      public int getInterruptedTaskCount() {
        return threadPool.getQueueSize();
      }

      @Override
      public void interrupt() {
        threadPool.shutdown();
      }

      @Override
      public boolean isFinished() {
        return threadPool.getQueueSize() == 0;
      }
    };
  }

}
