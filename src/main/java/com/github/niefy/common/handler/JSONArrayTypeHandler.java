package com.github.niefy.common.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
public class JSONArrayTypeHandler extends BaseTypeHandler<JSONArray> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONArray array, JdbcType jdbcType) throws SQLException {
        ps.setString(i,array.toJSONString());
    }

    @Override
    public JSONArray getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return JSONArray.parseArray(resultSet.getString(columnName));
    }

    @Override
    public JSONArray getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return JSONArray.parseArray(resultSet.getString(columnIndex));
    }

    @Override
    public JSONArray getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return JSONArray.parseArray(callableStatement.getString(columnIndex));
    }
}
