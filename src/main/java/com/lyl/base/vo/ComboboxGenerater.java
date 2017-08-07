package com.lyl.base.vo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;

public  class ComboboxGenerater{

    protected  String textFiled;
	
    protected String valueField;


	public ComboboxGenerater(String textFiled, String valueField) {
		super();
		Assert.notNull(textFiled, "The textFiled must not be null");
		Assert.notNull(valueField, "The valueField must not be null");
		this.textFiled = textFiled;
		this.valueField = valueField;
	}



	public  <T> List<ComboboxVo> generate(List<T> list,List<String> selectItems ) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<ComboboxVo> cvs=new ArrayList<ComboboxVo>();
		
		
		
		for(T t:list){
			String text=(String)PropertyUtils.getProperty(t, textFiled);
			String value=(String)PropertyUtils.getProperty(t, valueField);
			ComboboxVo cv=new ComboboxVo(value, text);
			if(selectItems.contains(value)){
				cv.setSelected(true);
			}else{
				cv.setSelected(false);
			}
			cvs.add(cv);
		}
		return cvs;
	}

	
	
	
}
