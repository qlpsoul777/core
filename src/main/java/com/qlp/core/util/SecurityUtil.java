package com.qlp.core.util;

import org.apache.shiro.SecurityUtils;

public class SecurityUtil {
	
	 public static String getCurrentUserLoginName() {
	        return (String) SecurityUtils.getSubject().getPrincipal();
	    }
}
