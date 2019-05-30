package ru.tiger20111;

public class Main {
  public static void main(String[] args) {
    BeanUtilsChecker first = new BeanUtilsChecker(1, 2, 3);
    BeanUtilsChecker second = new BeanUtilsChecker(3, 4, 5);
    BeanUtils.assign(first, second);

    System.out.println(first);
    System.out.println(second);
  }
}
