package com.qlp.july.core.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 重写toString方法
 * 
 * ToStringStyle参数说明：
 *	1. DEFAULT_STYLE
 *   	com.entity.Person@182f0db[name=John Doe,age=33,smoker=false]
 *   
 *	2. MULTI_LINE_STYLE
 *    	com.entity.Person@182f0db[
 *   		name=John Doe
 *   		age=33
 *   		smoker=false
 *		]
 *
 *	3. NO_FIELD_NAMES_STYLE
 *   	com.entity.Person@182f0db[John Doe,33,false]
 *   
 *	4. SHORT_PREFIX_STYLE   （即截去了包名）
 *  	Person[name=John Doe,age=33,smoker=false]
 *  
 *	5. SIMPLE_STYLE
 *   John Doe,33,false
 *
 *  6.JSON_STYLE
 *  	{"name":"John Doe","age":33,"smoker",false}
 * @author qlp
 *
 */
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3468337253574459082L;

	@Override
	public String toString(){
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
