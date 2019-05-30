import classloader.EncryptedClassLoader;

import java.io.File;
import java.lang.reflect.Method;

public class Main {
  public static void main(String[] args) {
    File dir = new File("target/test-classes");
    EncryptedClassLoader encryptedClassLoader = new EncryptedClassLoader("awesome_key", dir,
            ClassLoader.getSystemClassLoader());

    try {
      encryptedClassLoader.findClass("TestClass");
      Method method = encryptedClassLoader.loadClass("TestClass").getMethod("doSomething");

      method.invoke(encryptedClassLoader.loadClass("TestClass").newInstance());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
