package com.qlp.july.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页参数转换注解
 * @author qlp
 *
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PageRequestParam {
	
	/**	分页对象key	*/
	String  name() default "pageable";
	
	/**	分页条数	*/
	int size() default 10;
	
}
