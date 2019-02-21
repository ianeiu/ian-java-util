package com.ianeiu.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类操作工具类
 * @author FengHuayuan
 * @date 2018年2月4日 下午4:29:56.
 */
public final class ClassUtil {

	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	
	private ClassUtil() {

	}
	
	public static class EntryKeyValue {
		private String key;
		private Object value;

		private EntryKeyValue(String key, Object value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public boolean equals(Object value) {
			if (value == null) {
				throw new RuntimeException("值不能为空！");
			}
			return value.equals(this.value);
		}

		public static EntryKeyValue map(String key, Object value) {
			return new EntryKeyValue(key, value);
		}

	}

	/**
	 * 读取对象属性值
	 * @param propertyKey
	 * @param obj
	 * @return
	 */
	public static Object readFieldValue(String propertyKey, Object obj) {
		try {
			if (StringUtil.isBlank(propertyKey))
				return null;
			else if (obj == null)
				return null;
			PropertyDescriptor pd = new PropertyDescriptor(propertyKey, obj.getClass());
			Method readMethod = pd.getReadMethod();
			return readMethod.invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 构造
	 * @param clazz
	 * @param entryKeyValues
	 * @return
	 */
	public static <T> T construct(Class<T> clazz, EntryKeyValue... entryKeyValues) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			T obj = constructor.newInstance();
			if (entryKeyValues != null) {
				for (EntryKeyValue entryKeyValue : entryKeyValues) {
					writeFieldValue(entryKeyValue.getKey(), entryKeyValue.getValue(), obj);
				}
			}
			return obj;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("[" + clazz.getName() + "]类没有默认构造方法!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 编辑对象属性值
	 * @param propertyKey
	 * @param propertyValue
	 * @param obj
	 * @return
	 */
	public static boolean writeFieldValue(String propertyKey, Object propertyValue, Object obj) {
		return writeFieldValue(propertyKey, propertyValue, obj, false);
	}

	public static boolean writeFieldValue(String propertyKey, Object propertyValue, Object obj, boolean skipErr) {
		Class<?> clazz = obj.getClass();
		do {
			try {
				PropertyDescriptor pd = new PropertyDescriptor(propertyKey, clazz);
				Method writeMethod = pd.getWriteMethod();
				if (propertyValue != null) {
					if (writeMethod.getParameterTypes()[0] == Boolean.class) {
						if ("0".equals(propertyValue.toString())) {
							writeMethod.invoke(obj, Boolean.FALSE);
						} else if ("1".equals(propertyValue.toString())) {
							writeMethod.invoke(obj, Boolean.TRUE);
						}
					} else if (writeMethod.getParameterTypes()[0] == Integer.class) {
						writeMethod.invoke(obj, Integer.valueOf(propertyValue.toString()));
					} else if (writeMethod.getParameterTypes()[0] == Long.class) {
						writeMethod.invoke(obj, Long.valueOf(propertyValue.toString()));
					} else {
						writeMethod.invoke(obj, propertyValue);
					}
				}
				return true;
			} catch (IntrospectionException e) {
				continue;
			} catch (Exception e) {
				if (!skipErr) {
					throw new RuntimeException(e);
				}
				return false;
			}
		} while ((clazz = clazz.getSuperclass()) != null);
		return false;
	}

	/**
	 * 判断对象某属性是否为空
	 * @param propertyKey
	 * @param obj
	 * @return
	 */
	public static boolean checkNull(String propertyKey, Object obj) {
		Object value = readFieldValue(propertyKey, obj);
		if (value instanceof String) {
			return value == null || "".equals(((String) value).trim());
		}
		return value == null;
	}

	/**
	 * 判断该名字的对象属性是否含有注解(注意:不包含父类属性)
	 * @author FengHuayuan
	 * @date 2018年2月25日 下午1:42:54.
	 * @param obj
	 * @param name
	 * @param annotationClass
	 * @return
	 */
	public static boolean isFieldAnnotationPresent(Object obj, String name,
			Class<? extends Annotation> annotationClass) {
		if (obj == null) {
			throw new RuntimeException("传入的对象为空!");
		}
		try {
			Field field = obj.getClass().getDeclaredField(name);
			return field.isAnnotationPresent(annotationClass);
		} catch (NoSuchFieldException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("类[" + obj.getClass() + "]没有包含" + name + "属性!");
			}
			return false;
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得包下面的所有的class
	 * @author FengHuayuan
	 * @date 2018年4月21日 下午12:25:20.
	 * @param pack
	 * @return
	 */
	public static List<Class<?>> getClassesFromPackage(String pack) {
		List<Class<?>> clazzs = new ArrayList<Class<?>>();
		// 是否循环搜索子包
		boolean recursive = true;
		// 包名字
		String packageName = pack;
		// 包名对应的路径名称
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					logger.debug("*****【File类型】的扫描！");
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findClassInPackageByFile(packageName, filePath, recursive, clazzs);
				} else if ("jar".equals(protocol)) {
					logger.debug("*****【Jar类型】的扫描！");
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					getClasssFromJarFile(filePath, clazzs);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return clazzs;
	}

	/**
	 * 在package对应的路径下找到所有的class
	 * @author FengHuayuan
	 * @date 2018年4月21日 下午12:24:54.
	 * @param packageName
	 * @param filePath
	 * @param recursive
	 * @param clazzs
	 */
	private static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive,
			List<Class<?>> clazzs) {
		File dir = new File(filePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 在给定的目录下找到所有的文件，并且进行条件过滤
		File[] dirFiles = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				boolean acceptDir = recursive && file.isDirectory();// 接受dir目录
				boolean acceptClass = file.getName().endsWith("class");// 接受class文件
				return acceptDir || acceptClass;
			}
		});
		for (File file : dirFiles) {
			if (file.isDirectory()) {
				findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

    /**
     * 从jar文件中读取指定目录下面的所有的class文件
     * 	 * springboot打jar包专用
     * @param jarPath
     * @param clazzs
     */
	private static void getClasssFromJarFile(String jarPath,List<Class<?>> clazzs) {  

		logger.info("getClasssFromJarFile - jarPath:"+jarPath);//wm
		//jarPaht:file:/E:/IDEABuilder/fs/tyrz-springboot/tyrz-front/target/fsrzfw.jar!/BOOT-INF/classes!/cn/com/do1/component/identitySource/service/impl
		String[] jarPaths = jarPath.split("!");
		String jarPaht=jarPaths[0].substring(6);//去掉file:/
		String startDir = (jarPaths[1]+jarPaths[2]).substring(1);//去掉/
		
	    JarFile jarFile = null;  
	    try {  
	        jarFile = new JarFile(jarPaht);  
	    } catch (IOException e1) {  
	        e1.printStackTrace();  
	    }  
	  
	    List<JarEntry> jarEntryList = new ArrayList<JarEntry>();  
	  
	    Enumeration<JarEntry> ee = jarFile.entries();  
	    while (ee.hasMoreElements()) {  
	        JarEntry entry = (JarEntry) ee.nextElement();  
	        if (entry.getName().startsWith(startDir) && entry.getName().endsWith(".class")) {  
		        logger.info("getClasssFromJarFile - entry:"+entry.getName());//wm
	            jarEntryList.add(entry);  
	        }  
	    }  
	    for (JarEntry entry : jarEntryList) {  
	        String className = entry.getName().replace('/', '.');  
			//   BOOT-INF/classes/cn/com/do1/component/identitySource/service/impl/XXX.class
	        className = className.substring(17, className.length() - 6);//去掉  BOOT-INF/classes/     .class
	        try {  
	            clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(className));  
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	}
	
	private final static String PACKAGE_NAME = "com.wm.utils";

    /**
     * 获取类加载器
     */
    public static ClassLoader overridenClassLoader;

    public static ClassLoader getContextClassLoader() {
        return overridenClassLoader != null ?
                overridenClassLoader : Thread.currentThread().getContextClassLoader();
    }
    
    /**
     * 获取指定类的全部属性字段
     *
     * @param className    需要获取的类名
     * @param extendsField 是否获取接口或父类中的公共属性
     * @return 属性字段数组
     */
    public final static String[] getField(String className, boolean extendsField) {
        Class classz = loadClass(className);
        Field[] fields = classz.getFields();
        Set<String> set = new HashSet<>();
        if (fields != null) {
            for (Field f : fields) {
                set.add(f.getName());
            }
        }
        if (extendsField) {
            Field[] fieldz = classz.getDeclaredFields();
            if (fieldz != null) {
                for (Field f : fieldz) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * 获取类中的公共属性
     *
     * @param className    需要获取的类名
     * @param extendsField 是否获取接口或父类中的公共属性
     * @return 属性字段数组
     */
    public final static String[] getPublicField(String className, boolean extendsField) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet<>();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            for (Field f : fields) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("public")) {
                    set.add(f.getName());
                }
            }
        }
        if (extendsField) {
            Field[] fieldz = classz.getFields();
            if (fieldz != null) {
                for (Field f : fieldz) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * 获取类中定义的protected类型的属性字段
     *
     * @param className 需要获取的类名
     * @return protected类型的属性字段数组
     */
    public final static String[] getProtectedField(String className) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet<>();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            for (Field f : fields) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("protected")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * 获取类中定义的private类型的属性字段
     *
     * @param className 需要获取的类名
     * @return private类型的属性字段数组
     */
    public final static String[] getPrivateField(String className) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet<>();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            for (Field f : fields) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("private")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * 获取对象的全部public类型方法
     *
     * @param className     需要获取的类名
     * @param extendsMethod 是否获取继承来的方法
     * @return 方法名数组
     */
    public final static String[] getPublicMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }
        Set<String> set = new HashSet<>();
        if (methods != null) {
            for (Method f : methods) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("public")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }


    /**
     * 获取对象的全部protected类型方法
     *
     * @param className     需要获取的类名
     * @param extendsMethod 是否获取继承来的方法
     * @return 方法名数组
     */
    public final static String[] getProtectedMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }
        Set<String> set = new HashSet<>();
        if (methods != null) {
            for (Method f : methods) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("protected")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * 获取对象的全部private类型方法
     *
     * @param className 需要获取的类名
     * @return 方法名数组
     */
    public final static String[] getPrivateMethod(String className) {
        Class classz = loadClass(className);
        Method[] methods = classz.getDeclaredMethods();
        Set<String> set = new HashSet<>();
        if (methods != null) {
            for (Method f : methods) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("private")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * 获取对象的全部方法
     *
     * @param className     需要获取的类名
     * @param extendsMethod 是否获取继承来的方法
     * @return 方法名数组
     */
    public final static String[] getMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }
        Set<String> set = new HashSet<>();
        if (methods != null) {
            for (Method f : methods) {
                set.add(f.getName());
            }
        }
        return set.toArray(new String[set.size()]);
    }


    /**
     * 调用对象的setter方法
     *
     * @param obj   对象
     * @param att   属性名
     * @param value 属性值
     * @param type  属性类型
     */
    public final static void setter(Object obj, String att, Object value, Class<?> type)
            throws InvocationTargetException, IllegalAccessException {
        try {
            String name = att.substring(0, 1).toUpperCase() + att.substring(1);
            Method met = obj.getClass().getMethod("set" + name, type);
            met.invoke(obj, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取指定目录下所有的类名
     *
     * @param path         包名
     * @param childPackage 是否获取子包
     */
    public final static List<String> getClassName(String path, boolean childPackage) {
        List<String> fileNames = new ArrayList<>();
        if (path.endsWith(".jar")) {
            fileNames.addAll(getClassNameByJar(path));
        } else {
            fileNames = getClassNameByFile(path, childPackage);
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath     文件路径
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    public final static List<String> getClassNameByFile(String filePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<>();
        List<File> files = FileUtil.listFile(filePath, childPackage);
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                String childFilePath = file.getPath();
                int index = filePath.replaceAll("\\\\", ".").length();
                childFilePath = childFilePath.replaceAll("\\\\", ".").substring(index, childFilePath.length());
                myClassName.add(childFilePath);
            }
        }
        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @param jarPath jar文件路径
     * @return 类的完整名称
     */
    public final static List<String> getClassNameByJar(String jarPath) {
        List<String> myClassName = new ArrayList<>();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myClassName;
    }


    /**
     * 加载指定的类
     *
     * @param className 需要加载的类
     * @return 加载后的类
     */
    public final static Class loadClass(String className) {
        Class theClass = null;
        try {
            theClass = Class.forName(className);
        } catch (ClassNotFoundException e1) {
            logger.error("load class error:" + e1.getMessage());
            e1.printStackTrace();
        }
        return theClass;
    }

    /**
     * 获取jar包中的非*.class外的全部资源文件名字
     *
     * @param jarPath jar文件路径
     * @return 返回资源名称数组
     */
    public final static List<String> getResourceNameByJar(String jarPath) {
        List<String> resource = new ArrayList<>();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (!entryName.endsWith(".class") && !entryName.endsWith("/")) {
                    resource.add(FilePathUtil.commandPath(jarPath) + "!" + entryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }

    /**
     * 获取jar包中的非*.class外的全部的以suffix结尾的资源文件
     *
     * @param jarPath jar包的路径
     * @param suffix  后缀名称
     * @return 返回资源名称数组
     */
    public final static List<String> getResourceNameByJar(String jarPath, String suffix) {
        List<String> resource = new ArrayList<>();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(suffix)) {
                    resource.add(FilePathUtil.commandPath(jarPath) + "!" + entryName);
                }
            }
        } catch (IOException e) {
            logger.error(ExceptionUtil.stackTraceToString(e, PACKAGE_NAME));
        }
        return resource;
    }

    /**
     * 获取一个类的父类
     *
     * @param className 需要获取的类
     * @return 父类的名称
     */
    public final static String getSuperClass(String className) {
        Class classz = loadClass(className);
        Class superclass = classz.getSuperclass();
        return superclass.getName();
    }

    /**
     * 获取一个雷的继承链
     *
     * @param className 需要获取的类
     * @return 继承类名的数组
     */
    public final static String[] getSuperClassChian(String className) {
        Class classz = loadClass(className);
        List<String> list = new ArrayList<>();
        Class superclass = classz.getSuperclass();
        String superName = superclass.getName();
        if (!"java.lang.Object".equals(superName)) {
            list.add(superName);
            list.addAll(Arrays.asList(getSuperClassChian(superName)));
        } else {
            list.add(superName);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 获取一类实现的全部接口
     *
     * @param className         需要获取的类
     * @param extendsInterfaces 话说getInterfaces能全部获取到才对，然而测试的时候父类的接口并没有
     *                          因此就多除了这参数
     * @return 实现接口名称的数组
     */
    public final static String[] getInterfaces(String className, boolean extendsInterfaces) {
        Class classz = loadClass(className);
        List<String> list = new ArrayList<>();
        Class[] interfaces = classz.getInterfaces();
        if (interfaces != null) {
            for (Class inter : interfaces) {
                list.add(inter.getName());
            }
        }
        if (extendsInterfaces) {
            String[] superClass = getSuperClassChian(className);
            for (String c : superClass) {
                list.addAll(Arrays.asList(getInterfaces(c, false)));
            }
        }
        return list.toArray(new String[list.size()]);
    }
	

}
