package kr.mjc.lsh.basics.jdbc.user.dao;

import kr.mjc.lsh.basics.jdbc.DataSourceFactory;
import kr.mjc.lsh.basics.jdbc.DbException;
import kr.mjc.lsh.basics.jdbc.user.User;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Raw JDBC를 사용한 User Data Access Object
 *
 * @author Jacob
 */
@Slf4j
public class UserDaoImplUsingRawJdbc implements UserDao {

    private static final String LIST_USERS =
            "select userId, email, name from user order by userId desc limit ?,?";

    private static final String GET_USER =
            "select userId, email, name from user where userId=?";

    private static final String LOGIN =
            "select userId, email, name from user where email=? and password=sha2(?,256)";

    private static final String ADD_USER =
            "insert user(email, password, name) values(?,sha2(?,256),?)";

    private static final String UPDATE_PASSWORD =
            "update user set password=sha2(?,256) where userId=? and password=sha2(?,256)";

    private static final String DELETE_USER =
            "delete from user where userId=? and password=sha2(?,256)";

    private final DataSource ds;

    /**
     * 기본 생성자. 데이터소스를 초기화한다.
     */
    public UserDaoImplUsingRawJdbc() {
        ds = DataSourceFactory.getDataSource();
    }

    /**
     * 회원 목록
     *
     * @param count 목록의 갯수
     * @param page  페이지
     * @return 회원 목록
     */
    @Override
    public List<User> listUsers(int count, int page) {
        int offset = (page - 1) * count;  // 목록의 시작 시점
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(LIST_USERS)) {
            ps.setInt(1, offset);
            ps.setInt(2, count);
            List<User> userList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /**
     * 회원정보 조회
     *
     * @param userId 회원번호
     * @return 회원 정보
     */
    @Override
    public User getUser(int userId) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_USER)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /**
     * 로그인
     *
     * @param email    이메일
     * @param password 비밀번호
     * @return 로그인 성공하면 회원정보, 실패하면 NULL
     */
    @Override
    public User login(String email, String password) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(LOGIN)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /**
     * 회원 가입
     *
     * @param user 회원정보
     * @throws DbException 이메일 중복으로 회원가입 실패 시
     */
    @Override
    public void addUser(User user) throws DbException {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(ADD_USER,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                user.setUserId(rs.getInt("insert_id"));
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /**
     * 비밀번호 수정
     *
     * @param userId          회원번호
     * @param currentPassword 현재 비밀번호
     * @param newPassword     새 비밀번호
     * @return 수정 성공시 1, 회원이 없거나 비밀번호가 틀리면 0
     */
    @Override
    public int updatePassword(int userId, String currentPassword,
                              String newPassword) {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_PASSWORD)) {
            ps.setObject(1, newPassword); // new password
            ps.setObject(2, userId); // userId
            ps.setObject(3, currentPassword); // old password
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    /**
     * 회원 삭제
     *
     * @param userId   회원번호
     * @param password 비밀번호
     * @return 삭제 성공시 1, 회원번호가 없거나 비밀번호가 틀리면 0
     */
    @Override
    public int deleteUser(int userId, String password) throws DbException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_USER)) {
            ps.setInt(1, userId);
            ps.setString(2, password);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}