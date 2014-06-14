package pl.edu.agh.toik.bughandler.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Taras Melon & Jakub Kolodziej
 * 
 *         Help to find catch tasks and execute proceed method
 */
public class ReflectionHelper {

	/**
	 * Find classes implementing interface in package
	 * 
	 * @param interfaceClass
	 *            interface
	 * @param fromPackage
	 *            package
	 * @return found classes
	 */
	public static List<Class<?>> findClassesImplementing(
			final Class<?> interfaceClass, final Package fromPackage) {

		if (interfaceClass == null) {
			// Debug.println("Unknown subclass.");
			return null;
		}

		if (fromPackage == null) {
			// Debug.println("Unknown package.");
			return null;
		}

		final List<Class<?>> rVal = new ArrayList<Class<?>>();
		try {
			final Class<?>[] targets = getAllClassesFromPackage(fromPackage
					.getName());
			if (targets != null) {
				for (Class<?> aTarget : targets) {
					if (aTarget == null) {
						continue;
					} else if (aTarget.equals(interfaceClass)) {
						// Debug.println("Found the interface definition.");
						continue;
					} else if (!interfaceClass.isAssignableFrom(aTarget)) {
						// Debug.println("Class '" + aTarget.getName() +
						// "' is not a " + interfaceClass.getName());
						continue;
					} else {
						rVal.add(aTarget);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// Debug.println("Error reading package name.");
			// Debug.printStackTrace(e, Debug.LOW_LEVEL);
		} catch (IOException e) {
			// Debug.println("Error reading classes in package.");
			// Debug.printStackTrace(e, Debug.LOW_LEVEL);
		}

		return rVal;
	}

	/**
	 * Load all classes from a package.
	 * 
	 * @param packageName
	 *            package name
	 * @return all classes from a package
	 * @throws ClassNotFoundException
	 *             class not found
	 * @throws IOException
	 *             io exception
	 */
	public static Class<?>[] getAllClassesFromPackage(final String packageName)
			throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Find file in package.
	 * 
	 * @param directory
	 *            directory
	 * @param packageName
	 *            package
	 * @return classes in package
	 * @throws ClassNotFoundException
	 *             class not found
	 */
	public static List<Class<?>> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file,
						packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}
}
