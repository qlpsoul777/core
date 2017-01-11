package com.qlp.core.converter.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.qlp.core.enums.MyEnum;
import com.qlp.core.exception.MyException;

public class MyEnumCodeTypeHandler<E extends Enum<E>> extends BaseTypeHandler<MyEnum> {
	
	private Class<MyEnum> type;

	public MyEnumCodeTypeHandler(Class<MyEnum> type) {  
        if (type == null){
        	throw new MyException("Type argument cannot be null");  
        }
        this.type = type;  
    }
	
	private MyEnum convert(int code) {  
		MyEnum[] objs = type.getEnumConstants();  
        for (MyEnum em : objs) {  
            if (em.getCode() == code) {  
                return em;  
            }  
        }  
        return null;  
    } 
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, MyEnum parameter,
			JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getCode());
	}

	@Override
	public MyEnum getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return convert(rs.getInt(columnName)); 
	}

	@Override
	public MyEnum getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return convert(rs.getInt(columnIndex)); 
	}

	@Override
	public MyEnum getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return convert(cs.getInt(columnIndex));
	}

}
