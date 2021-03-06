package classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class EncryptedClassLoader extends ClassLoader {

  private final String key;

  private final File dir;


  public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
    super(parent);

    this.key = key;
    this.dir = dir;
  }

  private byte[] encryption(byte[] bytes) {
    int shift = 0;
    for (byte b : key.getBytes()) {
      shift += b;
    }

    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) ((byte) (bytes[i] + shift) % 256 - 128);
    }

    return bytes;
  }

  private byte[] decryption(byte[] bytes) {
    int shift = 0;
    for (byte b : key.getBytes()) {
      shift += b;
    }

    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) ((byte) (bytes[i] - shift) % 256 - 128);
    }

    return bytes;
  }

  private byte[] getBytesFromFile(String s) {
    byte[] bytes = {};

    try {
      File file = new File(dir + "/" + s + ".class");

      bytes = new byte[(int) file.length()];
      InputStream is = new FileInputStream(file);
      is.read(bytes);
      is.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return encryption(bytes);
  }

  @Override
  public Class<?> findClass(String s) throws ClassNotFoundException {
    byte[] bytes = getBytesFromFile(s);

    try {
      return defineClass(s, decryption(bytes), 0, bytes.length);
    } catch (Exception e) {
      return super.findClass(s);
    }
  }

  }
