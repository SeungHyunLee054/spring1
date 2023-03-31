package kr.mjc.lsh.basics.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ResultSet - Object 매핑을 위한 functional interface
 */
@FunctionalInterface
public interface ResultMapper<T> {
    T map(ResultSet rs) throws SQLException;
}