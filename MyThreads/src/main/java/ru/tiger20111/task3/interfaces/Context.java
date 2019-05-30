package ru.tiger20111.task3.interfaces;

public interface Context {

  int getCompletedTaskCount();

  int getFailedTaskCount();

  int getInterruptedTaskCount();

  void interrupt();

  boolean isFinished();
}

