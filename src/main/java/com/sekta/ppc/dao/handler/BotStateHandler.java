package com.sekta.ppc.dao.handler;

import com.sekta.ppc.dao.entity.enums.BotState;
import io.vavr.collection.Stream;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class BotStateHandler extends BaseTypeHandler<BotState> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BotState parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, serialize(parameter));
    }

    @Override public BotState getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return deserialize(rs.getInt(columnName));
    }

    @Override public BotState getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return deserialize(rs.getInt(columnIndex));
    }

    @Override public BotState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return deserialize(cs.getInt(columnIndex));
    }


    private Integer serialize(BotState value) {
        return value.getState();
    }

    private BotState deserialize(Integer id) {
        return Stream.ofAll(Arrays.asList(BotState.values()))
                .find(state -> state.getState().equals(id))
                .get();
    }
}