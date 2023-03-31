package kr.mjc.lsh.basics.jdbc;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC helper utilities
 *
 * @author Jacob
 */
@Slf4j
public class JdbcHelper {

    private final DataSource ds;

    /**
     * Default constructor. 데이터소스 초기화
     */
    public JdbcHelper(DataSource ds) throws DbException {
        this.ds = ds;
    }

    /**
     * 목록을 가져오는 helper method
     *
     * @return 결과 리스트. 결과가 없을 경우 빈 리스트
     */
    public <T> List<T> list(String sql, ResultMapper<T> mapper, Object... params)
            throws DbException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            setParameters(ps, params);
            ResultSet rs = ps.executeQuery();
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapper.map(rs));
            }
            return list;
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DbException(e);
        }
    }

    /**
     * 한 건을 가져오는 helper method
     *
     * @return 한 건 오브젝트. 결과가 없을 경우 null
     */
    public <T> T get(String sql, ResultMapper<T> mapper, Object... params)
            throws DbException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            setParameters(ps, params);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return mapper.map(rs);
            else
                return null;
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DbException(e);
        }
    }

    /**
     * 추가, 수정, 삭제하는 helper method
     *
     * @return 변경된 행의 갯수
     */
    public int update(String sql, Object... params) {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            setParameters(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DbException(e);
        }
    }

    /**
     * 추가하는 helper method. 자동 생성된 키들을 찾는다.
     *
     * @param generatedKeys 쿼리 수행 후에 자동 생성된 키가 들어감
     * @return 변경된 행의 갯수
     */
    public int insert(String sql, List<Integer> generatedKeys, Object... params) {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            setParameters(ps, params);
            int updatedRows = ps.executeUpdate();

            // 자동 생성 키 넣음
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next())
                generatedKeys.add(rs.getInt("insert_id"));
            rs.close();

            return updatedRows;
        } catch (SQLException e) {
            log.error(e.toString());
            throw new DbException(e);
        }
    }

    /**
     * preparedStatement에 파라미터를 설정한다.
     */
    private void setParameters(PreparedStatement ps, Object... params)
            throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++)
                ps.setObject(i + 1, params[i]);
        }
    }
}