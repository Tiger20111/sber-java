package ru.tiger20111.task1.resources;

import ru.tiger20111.task1.interfaces.ThreadPool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class FixedThreadPool implements ThreadPool {

  private final Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
  private volatile boolean isRunning = true;
  private final int numberOfThreads;

  private int completed = 0;
  private int failed = 0;


  private synchronized void incCompleted() {
    ++completed;
  }


  public int getCompletedTasks() {
    return completed;
  }


  private synchronized void incFailed() {
    ++failed;
  }


  public int getFailedTasks() {
    return failed;
  }


  public int getQueueSize() {
    return workQueue.size();
  }


  public FixedThreadPool(int numberOfThreads) {
    this.numberOfThreads = numberOfThreads;
  }


  @Override
  public void start() {
    for (int i = 0; i < numberOfThreads; i++) {
      new Thread(new Worker()).start();
    }
  }


  @Override
  public void execute(Runnable command) {
    if (isRunning) {
      workQueue.offer(command);
    }
  }


  public void shutdown() {
    isRunning = false;
  }


  private final class Worker implements Runnable {

    @Override
    public void run() {

      while (isRunning) {
        Runnable nextTask = workQueue.poll();
        if (nextTask != null) {
          try {
            nextTask.run();
            incCompleted();
          } catch (Exception e) {
            e.printStackTrace();
            incFailed();
          }
        }
      }
    }


  }
}
