package com.gmail.chickenpowerrr.chickentest.reflection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Some utility functions to help with reflection.
 *
 * @author Mark van Wijk
 * @since 1.0.0
 */
public final class ReflectionHelper {

  /**
   * Returns whether the given class has a public constructor
   * with the given parameters.
   *
   * @param targetClass the class to check
   * @param parameters the parameters the constructor should have
   * @return true if the constructor exists, false otherwise
   */
  public static boolean hasConstructor(Class<?> targetClass, Class<?> parameters) {
    try {
      targetClass.getConstructor(parameters);
      return true;
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  /**
   * Calls the constructor of a target class with the given arguments.
   *
   * @param targetClass the class we want an instance of
   * @param argumentTypes the types of arguments that the constructor should have
   * @param arguments the arguments that should be passed to the constructor
   * @param <T> the type that should be returned
   * @return the newly created instance
   * @throws NoSuchMethodException if the constructor doesn't exist
   * @throws InvocationTargetException if the reflection fails
   * @throws InstantiationException if the reflection fails
   * @throws IllegalAccessException if the reflection fails
   */
  public static <T> T getInstance(Class<T> targetClass, Class<?>[] argumentTypes, Object... arguments)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Constructor<T> constructor = targetClass.getConstructor(argumentTypes);

    return constructor.newInstance(arguments);
  }

  /**
   * Returns all the classes in a package.
   *
   * @param packageName the package that should be checked
   * @return the classes in the given package
   * @throws IOException should not happen
   * @throws ClassNotFoundException should not happen
   */
  public static List<Class<?>> getClasses(String packageName)
      throws IOException, ClassNotFoundException {
    return getClasses(packageName, c -> true);
  }

  /**
   * Returns all the classes in the given package that satisfy the predicate.
   *
   * @param packageName the package that should be checked
   * @param predicate whether the class should be added
   * @return the classes in the given package that satisfy the predicate
   * @throws IOException should not happen
   * @throws ClassNotFoundException should not happen
   */
  public static List<Class<?>> getClasses(String packageName, Predicate<Class<?>> predicate)
      throws IOException, ClassNotFoundException {
    List<Class<?>> classes = new LinkedList<>();
    ClassLoader classLoader = ReflectionHelper.class.getClassLoader();
    Enumeration<URL> urls = classLoader.getResources(packageName.replace(".", "/"));

    while (urls.hasMoreElements()) {
      classes.addAll(getClasses(new File(urls.nextElement().getFile()), packageName, predicate));
    }

    return classes;
  }

  /**
   * Returns all the classes in the given package that satisfy the predicate.
   *
   * @param packageFile the package that should be checked
   * @param packageName the name of the package
   * @param predicate whether the class should be added
   * @return the classes in the given package that satisfy the predicate
   * @throws ClassNotFoundException should not happen
   */
  private static List<Class<?>> getClasses(File packageFile, String packageName,
      Predicate<Class<?>> predicate) throws ClassNotFoundException {
    List<Class<?>> classes = new LinkedList<>();
    for (File file : Objects.requireNonNull(packageFile.listFiles())) {
      if (file.isDirectory()) {
        classes.addAll(getClasses(file, packageName + "." + file.getName(), predicate));
      } else if (file.getName().endsWith(".class")) {
        Class<?> candidate = Class.forName(packageName + "." + file.getName().replace(".class", ""));
        if (predicate.test(candidate)) {
          classes.add(candidate);
        }
      }
    }

    return classes;
  }

  private ReflectionHelper() {

  }
}
