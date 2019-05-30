package ru.tiger20111.task3;

import ru.tiger20111.task1.resources.FixedThreadPool;
import ru.tiger20111.task3.interfaces.Context;
import ru.tiger20111.task3.resources.MyExecutionManager;

import static java.lang.Thread.sleep;

public class Main {
  public static void main(String[] args) {
    FixedThreadPool fixedThreadPool = new FixedThreadPool(3);
    MyExecutionManager myExecutionManager = new MyExecutionManager(fixedThreadPool);

    Runnable task = () -> {
      double sum = 0;
      for (int i = 0; i < 20_000_000; ++i) {
        sum += Math.random();
      }

      System.out.println(Thread.currentThread().getName());
      System.out.println(sum);
    };

    Context context = myExecutionManager
            .execute(() -> System.out.println("It is callback "), task, task, task);

    try {
      sleep(7000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    context.interrupt();

    System.out.println(context.getCompletedTaskCount());

    System.out.println(context.getFailedTaskCount());

  }
}
