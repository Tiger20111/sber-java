package ru.tiger20111.task3.interfaces;

public interface ExecutionManager {

  Context execute(Runnable callback, Runnable... tasks);
}

