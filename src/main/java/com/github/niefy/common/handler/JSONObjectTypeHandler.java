package com.github.niefy.common.handler;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class JSONObjectTypeHandler extends BaseTypeHandler<JSONObject> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject array, JdbcType jdbcType) throws SQLException {
        ps.setString(i,array.toJSONString());
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return JSONObject.parseObject(resultSet.getString(columnName));
    }

    @Override
    public JSONObject getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return JSONObject.parseObject(resultSet.getString(columnIndex));
    }

    @Override
    public JSONObject getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return JSONObject.parseObject(callableStatement.getString(columnIndex));
    }
}
