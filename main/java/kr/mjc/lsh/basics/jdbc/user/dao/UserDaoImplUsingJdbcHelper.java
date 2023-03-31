package kr.mjc.lsh.basics.jdbc.user.dao;

import kr.mjc.lsh.basics.jdbc.DataSourceFactory;
import kr.mjc.lsh.basics.jdbc.DbException;
import kr.mjc.lsh.basics.jdbc.JdbcHelper;
import kr.mjc.lsh.basics.jdbc.user.User;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcHelper를 사용한 User Data Access Object
 *
 * @author Jacob
 */
@Slf4j
public class UserDaoImplUsingJdbcHelper implements UserDao {

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

    private final JdbcHelper jdbcHelper;

    /**
     * 기본 생성자. 데이터소스를 초기화한다.
     */
    public UserDaoImplUsingJdbcHelper() {
        DataSource ds = DataSourceFactory.getDataSource();
        jdbcHelper = new JdbcHelper(ds);
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
        return jdbcHelper.list(LIST_USERS, rs -> {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            return user;
        }, offset, count);
    }

    /**
     * 회원정보 조회
     *
     * @param userId 회원번호
     * @return 회원 정보
     */
    @Override
    public User getUser(int userId) {
        return jdbcHelper.get(GET_USER, rs -> {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            return user;
        }, userId);
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
        return jdbcHelper.get(LOGIN, rs -> {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setEmail(rs.getString("email"));
            user.setName(rs.getString("name"));
            return user;
        }, email, password);
    }

    /**
     * 회원 가입
     *
     * @param user 회원정보
     * @throws DbException 이메일 중복으로 회원가입 실패 시
     */
    @Override
    public void addUser(User user) throws DbException {
        List<Integer> generatedKeys = new ArrayList<>();
        jdbcHelper.insert(ADD_USER, generatedKeys, user.getEmail(),
                user.getPassword(), user.getName());
        user.setUserId(generatedKeys.get(0));
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
        return jdbcHelper.update(UPDATE_PASSWORD, newPassword, userId,
                currentPassword);
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
        return jdbcHelper.update(DELETE_USER, userId, password);
    }
}
