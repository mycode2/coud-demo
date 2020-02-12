package com.zc.study.common.utils;

import com.zc.study.common.annotation.AppModule;
import com.zc.study.common.constant.Constants;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClazzUtils extends org.springframework.util.ClassUtils implements ResourceLoaderAware {

	/**
	 * 注入ResourceLoader
	 */
	private static ResourceLoader resourceLoader;

	public static void getGenericClass(Type type,GenericClass genericClass){
		if(type==null)
			return ;
		if(type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			
			genericClass.setRawClass((Class<?>)parameterizedType.getRawType());
			
			Type[] args = parameterizedType.getActualTypeArguments();
			if(args!=null && args.length>0 ){
				GenericClass[] genericClasses = new GenericClass[args.length];
				for(int i=0;i<args.length;i++){
					Type t = args[i];
					GenericClass gc = new GenericClass();
					if(t instanceof ParameterizedType){
						getGenericClass(t,gc);
					}else{
						Class<?> clazz = (Class<?>)t;
						gc.setRawClass(clazz);
					}
					genericClasses[i]=gc;
				}
				genericClass.setGenericClasses(genericClasses);
			}
		}		
	}
	public static Class<?>[] getGenericClass(Type type){
		if(type == null){
			return null;
		}else if(type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			if(parameterizedType.getOwnerType()!=null){}
			Type[] args = parameterizedType.getActualTypeArguments();
			if(args!=null && args.length>0 ){
				Class<?>[] clazzs = new Class<?>[args.length];
				for(int i=0;i<args.length;i++){
					Type t = args[i];
					if(t instanceof ParameterizedType){
						ParameterizedType subType = (ParameterizedType) t;
						Class<?> rawClass = (Class<?>)parameterizedType.getRawType();
						if(Map.class.isAssignableFrom(rawClass)){
							return getGenericClass(t);
						}else{
							clazzs[i] = (Class<?>)subType.getRawType();
						}
					}else{						
						clazzs[i] = (Class<?>)args[i];
					}
				}
				return clazzs;
			}
		}
		return null;		
	}

	/**
	 * 获取到指定包下的全部类名集合
	 * @param basePackage
	 * @param classPattern
	 * @return
	 * @throws IOException
	 */

	public static List<String> findClassName (String basePackage, String classPattern) throws IOException {
		List<String> classNameList = new ArrayList<>();
		basePackage = basePackage.replace(Constants.SEPARATOR_IN_POINT,Constants.SEPARATOR_IN_SLASH);
		String basePackageWithResPath = org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage);
		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
				+ basePackageWithResPath + "/**/" + classPattern;
		ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
		MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);
		Resource[] resources = resolver.getResources(packageSearchPath);

		for (Resource resource : resources) {
			MetadataReader reader = metaReader.getMetadataReader(resource);
			String className = reader.getClassMetadata().getClassName();
			classNameList.add(className);
		}
		return classNameList;
	}

	public static String  getAppModule(String className) throws ClassNotFoundException {
		String moduleName=null;
		Class<?> aClass = Class.forName(className);
		if (aClass==null){
			return moduleName;
		}
		AppModule annotation = aClass.getAnnotation(AppModule.class);
		if (annotation == null){
			return moduleName;
		}
		return  annotation.moduleName();
	}

	public static Field[] getFieldsFromClass(String className) throws ClassNotFoundException {
		Class<?> aClass = Class.forName(className);
		Field[] fields = aClass.getFields();
		return fields;
	}


	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}
