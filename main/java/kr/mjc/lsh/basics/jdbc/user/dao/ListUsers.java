package kr.mjc.lsh.basics.jdbc.user.dao;

import kr.mjc.lsh.basics.jdbc.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;

/**
 * 회원 목록<br>
 * count와 page를 입력받아서 해당 목록을 가져온다.
 */
@Slf4j
public class ListUsers {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImplUsingJdbcHelper();
        Scanner scanner = new Scanner(System.in);
        System.out.print("List - count page : ");
        int[] params = {scanner.nextInt(), scanner.nextInt()};
        scanner.close();

        List<User> userList = userDao.listUsers(params[0], params[1]);
        log.debug(userList.toString());
    }
}