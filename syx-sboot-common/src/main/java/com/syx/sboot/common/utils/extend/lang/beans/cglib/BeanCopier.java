package com.syx.sboot.common.utils.extend.lang.beans.cglib;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.syx.sboot.common.utils.extend.lang.utils.ArrayUtil;
import net.sf.cglib.core.*;
import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public abstract class BeanCopier
{
	  private static final Logger logger = LoggerFactory.getLogger("BeanCopier");

	  private static final BeanCopierKey KEY_FACTORY = (BeanCopierKey)KeyFactory.create(BeanCopierKey.class);

	  private static final Type CONVERTER = TypeUtils.parseType("Converter");

	  private static final Type BEAN_COPIER = TypeUtils.parseType("BeanCopier");

	  private static final Signature COPY = new Signature("copy", Type.VOID_TYPE, new Type[] { Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER });

	  private static final Signature CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");

	  private static final Map<Key, BeanCopier> copierMap = Maps.newConcurrentMap();

	  public static BeanCopier create(Class source, Class target, boolean useConverter)
	  {
	    Generator gen = new Generator();
	    gen.setSource(source);
	    gen.setTarget(target);
	    gen.setUseConverter(useConverter);
	    return gen.create();
	  }

	  public static BeanCopier create(Class source, Class target, String[] ignorePropeties) {
	    Generator gen = new Generator();
	    gen.setSource(source);
	    gen.setTarget(target);
	    gen.setUseConverter(false);
	    gen.setIgnorePropeties(ignorePropeties);
	    return gen.create();
	  }

	  public abstract void copy(Object paramObject1, Object paramObject2, Converter paramConverter);

	  private static Set<String> convertPropertiesToSetter(String[] ignoreProperties)
	  {
	    Set set = Sets.newHashSet();
	    for (String p : ignoreProperties)
	      if (!StringUtils.isBlank(p))
	      {
	        StringBuilder sb = new StringBuilder();
	        sb.append("set");
	        sb.append(Character.toUpperCase(p.charAt(0)));
	        sb.append(p.substring(1, p.length()));
	        set.add(sb.toString());
	      }
	    return set;
	  }

	  public static void staticCopy(Object from, Object to)
	  {
	    staticCopy(from, to, new String[] {});
	  }

	  public static void staticCopy2(Object from, Object to, Converter converter)
	  {
	    Assert.notNull(from, "源对象不能为空");
	    Assert.notNull(to, "目标对象不能为空");
	    boolean useConverter = converter != null;
	    Key key = getKey(from, to, useConverter);
	    if (!copierMap.containsKey(key)) {
	      synchronized (BeanCopier.class) {
	        if (!copierMap.containsKey(key)) {
	          BeanCopier copy = create(from.getClass(), to.getClass(), useConverter);

	          copierMap.put(key, copy);
	        }
	      }
	    }
	    staticCopy(from, to, converter, key);
	  }

	  public static void staticCopy(Object from, Object to, String[] ignorePropeties)
	  {
	    Assert.notNull(from, "源对象不能为空");
	    Assert.notNull(to, "目标对象不能为空");
	    Key key = getKey(from, to, false, ignorePropeties);
	    BeanCopier beanCopier = (BeanCopier)copierMap.get(key);
	    if (beanCopier == null) {
	      synchronized (BeanCopier.class) {
	        beanCopier = (BeanCopier)copierMap.get(key);
	        if (beanCopier == null) {
	          BeanCopier copy = create(from.getClass(), to.getClass(), ignorePropeties);

	          copierMap.put(key, copy);
	          beanCopier = copy;
	        }
	        if (beanCopier != null)
	          beanCopier.copy(from, to, null);
	      }
	    }
	    else
	      beanCopier.copy(from, to, null);
	  }

	  private static void staticCopy(Object from, Object to, Converter converter, Key key) {
	    BeanCopier copy = (BeanCopier)copierMap.get(key);
	    copy.copy(from, to, converter);
	  }

	  private static Key getKey(Object from, Object to, boolean useConverter) {
	    Class fromClass = from.getClass();
	    Class toClass = to.getClass();
	    return new Key(fromClass, toClass, useConverter);
	  }

	  private static Key getKey(Object from, Object to, boolean useConverter, String[] ignoreProperties)
	  {
	    Class fromClass = from.getClass();
	    Class toClass = to.getClass();
	    return new Key(fromClass, toClass, useConverter, ignoreProperties);
	  }


	  private static class Key
	  {
	    private Class<?> fromClass;
	    private Class<?> toClass;
	    private boolean useConverter;
	    private String[] ignoreProperties;

	    public Key(Class<?> fromClass, Class<?> toClass, boolean useConverter)
	    {
	      this.fromClass = fromClass;
	      this.toClass = toClass;
	      this.useConverter = useConverter;
	    }

	    public Key(Class<?> fromClass, Class<?> toClass, boolean useConverter, String[] ignoreProperties)
	    {
	      this.fromClass = fromClass;
	      this.toClass = toClass;
	      this.useConverter = useConverter;
	      this.ignoreProperties = ignoreProperties;
	    }

	    public boolean equals(Object o)
	    {
	      if (this == o)
	        return true;
	      if ((o == null) || (getClass() != o.getClass())) {
	        return false;
	      }
	      Key key = (Key)o;

	      if (this.useConverter != key.useConverter)
	        return false;
	      if (!this.fromClass.equals(key.fromClass))
	        return false;
	      if (!Arrays.equals(this.ignoreProperties, key.ignoreProperties))
	        return false;
	      if (!this.toClass.equals(key.toClass)) {
	        return false;
	      }
	      return true;
	    }

	    public int hashCode()
	    {
	      int result = this.fromClass.hashCode();
	      result = 31 * result + this.toClass.hashCode();
	      result = 31 * result + (this.useConverter ? 1 : 0);
	      result = 31 * result + (this.ignoreProperties != null ? Arrays.hashCode(this.ignoreProperties) : 0);

	      return result;
	    }
	  }

	  public static class Generator extends AbstractClassGenerator
	  {
	    private static final Source SOURCE = new Source(BeanCopier.class.getName());
	    private Class source;
	    private Class target;
	    private boolean useConverter;
	    private String[] ignorePropeties;

	    public Generator()
	    {
	      super(SOURCE);
	    }

	    public void setSource(Class source) {
	      if (!Modifier.isPublic(source.getModifiers())) {
	        setNamePrefix(source.getName());
	      }
	      this.source = source;
	    }

	    public void setTarget(Class target) {
	      if (!Modifier.isPublic(target.getModifiers())) {
	        setNamePrefix(target.getName());
	      }

	      this.target = target;
	    }

	    public String[] getIgnorePropeties() {
	      return this.ignorePropeties;
	    }

	    public void setIgnorePropeties(String[] ignorePropeties) {
	      this.ignorePropeties = ignorePropeties;
	    }

	    public void setUseConverter(boolean useConverter) {
	      this.useConverter = useConverter;
	    }

	    protected ClassLoader getDefaultClassLoader() {
	      return this.source.getClassLoader();
	    }

	    public BeanCopier create() {
	      Object key = BeanCopier.KEY_FACTORY.newInstance(this.source.getName(), this.target.getName(), this.useConverter);
	      return (BeanCopier)super.create(key);
	    }

	    public void generateClass(ClassVisitor v) {
	      Type sourceType = Type.getType(this.source);
	      Type targetType = Type.getType(this.target);
	      ClassEmitter ce = new ClassEmitter(v);
	      ce.begin_class(46, 1, getClassName(), BeanCopier.BEAN_COPIER, null, "<generated>");

	      EmitUtils.null_constructor(ce);
	      CodeEmitter e = ce.begin_method(1, BeanCopier.COPY, null);
	      PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(this.source);
	      PropertyDescriptor[] setters = ReflectUtils.getBeanGetters(this.target);

	      Map names = new HashMap();
	      for (int i = 0; i < getters.length; i++) {
	        names.put(getters[i].getName(), getters[i]);
	      }
	      Local targetLocal = e.make_local();
	      Local sourceLocal = e.make_local();
	      if (this.useConverter) {
	        e.load_arg(1);
	        e.checkcast(targetType);
	        e.store_local(targetLocal);
	        e.load_arg(0);
	        e.checkcast(sourceType);
	        e.store_local(sourceLocal);
	      } else {
	        e.load_arg(1);
	        e.checkcast(targetType);
	        e.load_arg(0);
	        e.checkcast(sourceType);
	      }
	      for (PropertyDescriptor setter : setters)
	        if (this.ignorePropeties != null) {
	          String propertyName = setter.getName();
	          if (ArrayUtil.contains(this.ignorePropeties, propertyName));
	        }
	        else {
	          PropertyDescriptor getter = (PropertyDescriptor)names.get(setter.getName());
	          if (getter == null) {
	            BeanCopier.logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", this.source.getCanonicalName(), setter.getName());
	          }
	          else
	          {
	            Method readMethod = getter.getReadMethod();
	            if (readMethod == null) {
	              BeanCopier.logger.debug("[对象属性复制]原对象[{}.{}]getter方法不存在", this.source.getCanonicalName(), getter.getName());
	            }
	            else
	            {
	              Method writeMethod = setter.getWriteMethod();
	              if (writeMethod == null) {
	                BeanCopier.logger.debug("[对象属性复制]目标对象[{}.{}]setter方法不存在", this.target.getCanonicalName(), setter.getName());
	              }
	              else
	              {
	                MethodInfo read = ReflectUtils.getMethodInfo(readMethod);
	                MethodInfo write = ReflectUtils.getMethodInfo(writeMethod);
	                if (this.useConverter) {
	                  Type setterType = write.getSignature().getArgumentTypes()[0];
	                  e.load_local(targetLocal);
	                  e.load_arg(2);
	                  e.load_local(sourceLocal);
	                  e.invoke(read);
	                  e.box(read.getSignature().getReturnType());
	                  EmitUtils.load_class(e, setterType);
	                  e.push(write.getSignature().getName());
	                  e.invoke_interface(BeanCopier.CONVERTER, BeanCopier.CONVERT);
	                  e.unbox_or_zero(setterType);
	                  e.invoke(write);
	                } else if (compatible(getter, setter)) {
	                  e.dup2();
	                  e.invoke(read);
	                  e.invoke(write);
	                }
	              }
	            }
	          }
	        }
	      e.return_value();
	      e.end_method();
	      ce.end_class();
	    }

	    private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter)
	    {
	      boolean match = setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
	      if (!match) {
	        BeanCopier.logger.debug("[对象属性复制]属性类型不匹配{}.{}({})->{}.{}({})", new Object[] { getter.getReadMethod().getDeclaringClass().getSimpleName(), getter.getName(), getter.getPropertyType(), setter.getWriteMethod().getDeclaringClass().getSimpleName(), setter.getName(), setter.getPropertyType() });
	      }

	      return match;
	    }

	    protected Object firstInstance(Class type) {
	      return ReflectUtils.newInstance(type);
	    }

	    protected Object nextInstance(Object instance) {
	      return instance;
	    }
	  }

	  static abstract interface BeanCopierKey
	  {
	    public abstract Object newInstance(String paramString1, String paramString2, boolean paramBoolean);
	  }
}