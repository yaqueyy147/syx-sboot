// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ClassUtil.java

package com.syx.sboot.common.utils.extend.lang.utils;

import java.lang.reflect.Array;
import java.util.*;

// Referenced classes of package com.yjf.common.lang.util:
//			StringUtil, ArrayUtil

public class ClassUtil {
	public static final char RESOURCE_SEPARATOR_CHAR = '/';
	public static final char PACKAGE_SEPARATOR_CHAR = '.';
	public static final String PACKAGE_SEPARATOR = String.valueOf('.');
	public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
	public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');

	private static Map<Class, TypeInfo> TYPE_MAP = Collections.synchronizedMap(new WeakHashMap());

	public static String getClassNameForObject(Object object) {
		if (object == null) {
			return null;
		}

		return getClassName(object.getClass().getName(), true);
	}

	public static String getClassName(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getClassName(clazz.getName(), true);
	}

	public static String getClassName(String className) {
		return getClassName(className, true);
	}

	private static String getClassName(String className, boolean processInnerClass) {
		if (StringUtil.isEmpty(className)) {
			return className;
		}

		if (processInnerClass) {
			className = className.replace('$', '.');
		}

		int length = className.length();
		int dimension = 0;

		for (int i = 0; (i < length) && (className.charAt(i) == '['); dimension++)
			i++;

		if (dimension == 0) {
			return className;
		}

		if (length <= dimension) {
			return className;
		}

		StringBuffer componentTypeName = new StringBuffer();

		switch (className.charAt(dimension)) {
		case 'Z':
			componentTypeName.append("boolean");
			break;
		case 'B':
			componentTypeName.append("byte");
			break;
		case 'C':
			componentTypeName.append("char");
			break;
		case 'D':
			componentTypeName.append("double");
			break;
		case 'F':
			componentTypeName.append("float");
			break;
		case 'I':
			componentTypeName.append("int");
			break;
		case 'J':
			componentTypeName.append("long");
			break;
		case 'S':
			componentTypeName.append("short");
			break;
		case 'L':
			if ((className.charAt(length - 1) != ';') || (length <= dimension + 2)) {
				return className;
			}

			componentTypeName.append(className.substring(dimension + 1, length - 1));
			break;
		case 'E':
		case 'G':
		case 'H':
		case 'K':
		case 'M':
		case 'N':
		case 'O':
		case 'P':
		case 'Q':
		case 'R':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		default:
			return className;
		}

		for (int i = 0; i < dimension; i++) {
			componentTypeName.append("[]");
		}

		return componentTypeName.toString();
	}

	public static String getShortClassNameForObject(Object object) {
		if (object == null) {
			return null;
		}

		return getShortClassName(object.getClass().getName());
	}

	public static String getShortClassName(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getShortClassName(clazz.getName());
	}

	public static String getShortClassName(String className) {
		if (StringUtil.isEmpty(className)) {
			return className;
		}

		className = getClassName(className, false);

		char[] chars = className.toCharArray();
		int lastDot = 0;

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '.')
				lastDot = i + 1;
			else if (chars[i] == '$') {
				chars[i] = '.';
			}
		}

		return new String(chars, lastDot, chars.length - lastDot);
	}

	public static String getPackageNameForObject(Object object) {
		if (object == null) {
			return null;
		}

		return getPackageName(object.getClass().getName());
	}

	public static String getPackageName(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getPackageName(clazz.getName());
	}

	public static String getPackageName(String className) {
		if (StringUtil.isEmpty(className)) {
			return null;
		}

		className = getClassName(className, false);

		int i = className.lastIndexOf('.');

		if (i == -1) {
			return "";
		}

		return className.substring(0, i);
	}

	public static String getClassNameForObjectAsResource(Object object) {
		if (object == null) {
			return null;
		}

		return object.getClass().getName().replace('.', '/') + ".class";
	}

	public static String getClassNameAsResource(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return clazz.getName().replace('.', '/') + ".class";
	}

	public static String getClassNameAsResource(String className) {
		if (className == null) {
			return null;
		}

		return className.replace('.', '/') + ".class";
	}

	public static String getPackageNameForObjectAsResource(Object object) {
		if (object == null) {
			return null;
		}

		return getPackageNameForObject(object).replace('.', '/');
	}

	public static String getPackageNameAsResource(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getPackageName(clazz).replace('.', '/');
	}

	public static String getPackageNameAsResource(String className) {
		if (className == null) {
			return null;
		}

		return getPackageName(className).replace('.', '/');
	}

	public static Class<?> getArrayClass(Class<?> componentType, int dimension) {
		if (dimension <= 0) {
			return componentType;
		}

		if (componentType == null) {
			return null;
		}

		return Array.newInstance(componentType, new int[dimension]).getClass();
	}

	public static Class<?> getArrayComponentType(Class<?> type) {
		if (type == null) {
			return null;
		}

		return getTypeInfo(type).getArrayComponentType();
	}

	public static int getArrayDimension(Class<?> clazz) {
		if (clazz == null) {
			return -1;
		}

		return getTypeInfo(clazz).getArrayDimension();
	}

	public static List<Class> getSuperclasses(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getTypeInfo(clazz).getSuperclasses();
	}

	public static List<Class> getInterfaces(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		return getTypeInfo(clazz).getInterfaces();
	}

	public static boolean isInnerClass(Class<?> clazz) {
		if (clazz == null) {
			return false;
		}

		return StringUtil.contains(clazz.getName(), '$');
	}

	public static boolean isAssignable(Class[] classes, Class[] fromClasses) {
		if (!ArrayUtil.isSameLength(fromClasses, classes)) {
			return false;
		}

		if (fromClasses == null) {
			fromClasses = ArrayUtil.EMPTY_CLASS_ARRAY;
		}

		if (classes == null) {
			classes = ArrayUtil.EMPTY_CLASS_ARRAY;
		}

		for (int i = 0; i < fromClasses.length; i++) {
			if (!isAssignable(classes[i], fromClasses[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean isAssignable(Class<?> clazz, Class<?> fromClass) {
		if (clazz == null) {
			return false;
		}

		if (fromClass == null) {
			return !clazz.isPrimitive();
		}

		if (clazz.isAssignableFrom(fromClass)) {
			return true;
		}

		if (clazz.isPrimitive()) {
			if (Boolean.TYPE.equals(clazz)) {
				return Boolean.class.equals(fromClass);
			}

			if (Byte.TYPE.equals(clazz)) {
				return Byte.class.equals(fromClass);
			}

			if (Character.TYPE.equals(clazz)) {
				return Character.class.equals(fromClass);
			}

			if (Short.TYPE.equals(clazz)) {
				return (Short.class.equals(fromClass)) || (Byte.TYPE.equals(fromClass)) || (Byte.class.equals(fromClass));
			}

			if (Integer.TYPE.equals(clazz)) {
				return (Integer.class.equals(fromClass)) || (Byte.TYPE.equals(fromClass)) || (Byte.class.equals(fromClass)) || (Short.TYPE.equals(fromClass)) || (Short.class.equals(fromClass)) || (Character.TYPE.equals(fromClass))
						|| (Character.class.equals(fromClass));
			}

			if (Long.TYPE.equals(clazz)) {
				return (Long.class.equals(fromClass)) || (Integer.TYPE.equals(fromClass)) || (Integer.class.equals(fromClass)) || (Byte.TYPE.equals(fromClass)) || (Byte.class.equals(fromClass)) || (Short.TYPE.equals(fromClass))
						|| (Short.class.equals(fromClass)) || (Character.TYPE.equals(fromClass)) || (Character.class.equals(fromClass));
			}

			if (Float.TYPE.equals(clazz)) {
				return (Float.class.equals(fromClass)) || (Long.TYPE.equals(fromClass)) || (Long.class.equals(fromClass)) || (Integer.TYPE.equals(fromClass)) || (Integer.class.equals(fromClass)) || (Byte.TYPE.equals(fromClass))
						|| (Byte.class.equals(fromClass)) || (Short.TYPE.equals(fromClass)) || (Short.class.equals(fromClass)) || (Character.TYPE.equals(fromClass)) || (Character.class.equals(fromClass));
			}

			if (Double.TYPE.equals(clazz)) {
				return (Double.class.equals(fromClass)) || (Float.TYPE.equals(fromClass)) || (Float.class.equals(fromClass)) || (Long.TYPE.equals(fromClass)) || (Long.class.equals(fromClass)) || (Integer.TYPE.equals(fromClass))
						|| (Integer.class.equals(fromClass)) || (Byte.TYPE.equals(fromClass)) || (Byte.class.equals(fromClass)) || (Short.TYPE.equals(fromClass)) || (Short.class.equals(fromClass)) || (Character.TYPE.equals(fromClass))
						|| (Character.class.equals(fromClass));
			}

		}

		return false;
	}

	protected static TypeInfo getTypeInfo(Class<?> type) {
		if (type == null)
			throw new IllegalArgumentException("Parameter clazz should not be null");
		TypeInfo classInfo;
		synchronized (TYPE_MAP) {
			classInfo = (TypeInfo) TYPE_MAP.get(type);

			if (classInfo == null) {
				classInfo = new TypeInfo(type);
				TYPE_MAP.put(type, classInfo);
			}
		}

		return classInfo;
	}

	public static Class<?> getPrimitiveType(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		if (clazz.isPrimitive()) {
			return clazz;
		}

		if (clazz.equals(Long.class)) {
			return Long.TYPE;
		}

		if (clazz.equals(Integer.class)) {
			return Integer.TYPE;
		}

		if (clazz.equals(Short.class)) {
			return Short.TYPE;
		}

		if (clazz.equals(Byte.class)) {
			return Byte.TYPE;
		}

		if (clazz.equals(Double.class)) {
			return Double.TYPE;
		}

		if (clazz.equals(Float.class)) {
			return Float.TYPE;
		}

		if (clazz.equals(Boolean.class)) {
			return Boolean.TYPE;
		}

		if (clazz.equals(Character.class)) {
			return Character.TYPE;
		}

		return null;
	}

	public static Class<?> getNonPrimitiveType(Class<?> clazz) {
		if (clazz == null) {
			return null;
		}

		if (!clazz.isPrimitive()) {
			return clazz;
		}

		if (clazz.equals(Long.TYPE)) {
			return Long.class;
		}

		if (clazz.equals(Integer.TYPE)) {
			return Integer.class;
		}

		if (clazz.equals(Short.TYPE)) {
			return Short.class;
		}

		if (clazz.equals(Byte.TYPE)) {
			return Byte.class;
		}

		if (clazz.equals(Double.TYPE)) {
			return Double.class;
		}

		if (clazz.equals(Float.TYPE)) {
			return Float.class;
		}

		if (clazz.equals(Boolean.TYPE)) {
			return Boolean.class;
		}

		if (clazz.equals(Character.TYPE)) {
			return Character.class;
		}

		return null;
	}

	protected static class TypeInfo {
		private final Class<?> type;
		private final Class<?> componentType;
		private int dimension;
		private final List<Class> superclasses = new ArrayList(2);
		private final List<Class> interfaces = new ArrayList(2);

		private TypeInfo(Class<?> type) {
			this.type = type;

			Class componentType = null;

			if (type.isArray()) {
				componentType = type;
				do {
					componentType = componentType.getComponentType();
					this.dimension += 1;
				} while (componentType.isArray());
			}

			this.componentType = componentType;

			if (this.dimension > 0) {
				componentType = getNonPrimitiveType(componentType);

				Class superComponentType = componentType.getSuperclass();

				if ((superComponentType == null) && (!Object.class.equals(componentType))) {
					superComponentType = Object.class;
				}

				if (superComponentType != null) {
					Class superclass = ClassUtil.getArrayClass(superComponentType, this.dimension);

					this.superclasses.add(superclass);
					this.superclasses.addAll(ClassUtil.getTypeInfo(superclass).superclasses);
				} else {
					for (int i = this.dimension - 1; i >= 0; i--)
						this.superclasses.add(ClassUtil.getArrayClass(Object.class, i));
				}
			} else {
				type = getNonPrimitiveType(type);

				Class superclass = type.getSuperclass();

				if (superclass != null) {
					this.superclasses.add(superclass);
					this.superclasses.addAll(ClassUtil.getTypeInfo(superclass).superclasses);
				}
			}
			Iterator i;
			if (this.dimension == 0) {
				Class[] typeInterfaces = type.getInterfaces();
				List set = new ArrayList();

				for (int j = 0; j < typeInterfaces.length; j++) {
					Class typeInterface = typeInterfaces[j];

					set.add(typeInterface);
					set.addAll(ClassUtil.getTypeInfo(typeInterface).interfaces);
				}

				for (i = this.superclasses.iterator(); i.hasNext();) {
					Class typeInterface = (Class) i.next();

					set.addAll(ClassUtil.getTypeInfo(typeInterface).interfaces);
				}

				for (i = set.iterator(); i.hasNext();) {
					Class interfaceClass = (Class) i.next();

					if (!this.interfaces.contains(interfaceClass))
						this.interfaces.add(interfaceClass);
				}
			} else {
				i = ClassUtil.getTypeInfo(componentType).interfaces.iterator();
				while (i.hasNext()) {
					Class componentInterface = (Class) i.next();

					this.interfaces.add(ClassUtil.getArrayClass(componentInterface, this.dimension));
				}
			}
		}

		private Class<?> getNonPrimitiveType(Class<?> type) {
			if (type.isPrimitive()) {
				if (Integer.TYPE.equals(type))
					type = Integer.class;
				else if (Long.TYPE.equals(type))
					type = Long.class;
				else if (Short.TYPE.equals(type))
					type = Short.class;
				else if (Byte.TYPE.equals(type))
					type = Byte.class;
				else if (Float.TYPE.equals(type))
					type = Float.class;
				else if (Double.TYPE.equals(type))
					type = Double.class;
				else if (Boolean.TYPE.equals(type))
					type = Boolean.class;
				else if (Character.TYPE.equals(type)) {
					type = Character.class;
				}
			}

			return type;
		}

		public Class<?> getType() {
			return this.type;
		}

		public Class<?> getArrayComponentType() {
			return this.componentType;
		}

		public int getArrayDimension() {
			return this.dimension;
		}

		public List<Class> getSuperclasses() {
			return Collections.unmodifiableList(this.superclasses);
		}

		public List<Class> getInterfaces() {
			return Collections.unmodifiableList(this.interfaces);
		}
	}

}
