package com.zc.study.common.utils;

public class GenericClass {

	private Class<?> rawClass;
	
	private GenericClass[] genericClasses ;

	public Class<?> getRawClass() {
		return rawClass;
	}

	public void setRawClass(Class<?> rawClass) {
		this.rawClass = rawClass;
	}

	public GenericClass[] getGenericClasses() {
		return genericClasses;
	}

	public void setGenericClasses(GenericClass[] genericClasses) {
		this.genericClasses = genericClasses;
	}
	
	public String toString(){
		StringBuffer buff = new StringBuffer("[rawClass:"+rawClass);
		
		if(genericClasses!=null){
			buff.append(",genericClasses:{");
			for(GenericClass gc :  genericClasses){
				buff.append(gc);
			}
			buff.append("}");
		}
		buff.append("]");
		return buff.toString();
	}
}
