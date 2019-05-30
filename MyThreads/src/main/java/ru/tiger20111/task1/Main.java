package ru.tiger20111.task1;

import ru.tiger20111.task1.resources.FixedThreadPool;
import ru.tiger20111.task1.resources.ScalableThreadPool;

public class Main {
  public static void main(String[] args) {
    //FixedThreadPool threadPool = new FixedThreadPool(5);
    ScalableThreadPool threadPool = new ScalableThreadPool(2, 6);
    threadPool.start();

    for (int j = 0; j < 10; ++j) {
      threadPool.execute(() -> {
        double sum = 0;
        for (int i = 0; i < 10_000_000; ++i) {
          sum += Math.random();
        }

        System.out.println(Thread.currentThread().getName());
        System.out.println(sum);
      });
    }

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    threadPool.shutdown();


  }
}
