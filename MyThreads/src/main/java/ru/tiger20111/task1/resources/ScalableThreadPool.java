package ru.tiger20111.task1.resources;


import ru.tiger20111.task1.interfaces.ThreadPool;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ScalableThreadPool implements ThreadPool {
  private final int minThreadsNum;
  private final int maxThreadsNum;

  private final Worker[] threads;
  private final ArrayList<Worker> extraThreads;

  private final LinkedBlockingQueue<Runnable> tasks;

  private volatile boolean isRunning = true;

  public ScalableThreadPool(int min_threads_num, int max_threads_num) {
    this.minThreadsNum = min_threads_num;
    this.maxThreadsNum = max_threads_num;

    threads = new Worker[min_threads_num];
    extraThreads = new ArrayList<Worker>();
    tasks = new LinkedBlockingQueue<Runnable>();
  }

  public void start() {
    for (int i = 0; i < minThreadsNum; i++) {
      threads[i] = new Worker();
      threads[i].start();
    }
  }

  public void execute(Runnable runnable) {
    if (isRunning)
    for (Worker worker: extraThreads) {
      if (!worker.isAlive()) {
        extraThreads.remove(worker);
      }
    }

    synchronized (tasks) {
      if (!tasks.isEmpty() &&
              extraThreads.size() < (maxThreadsNum - minThreadsNum)) {
        Worker extra_worker = new Worker();
        extra_worker.start();
        extraThreads.add(extra_worker);

      }
      tasks.add(runnable);
      tasks.notify();
    }
  }

  public void shutdown() {
    isRunning = false;
  }

  private class Worker extends Thread {
    public void run() {
      Runnable cur_task;

      while(isRunning) {
        synchronized (tasks) {
          while (tasks.isEmpty() && isRunning) {
            try {
              tasks.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          cur_task = tasks.poll();
        }

        try {
          cur_task.run();
        } catch (RuntimeException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
