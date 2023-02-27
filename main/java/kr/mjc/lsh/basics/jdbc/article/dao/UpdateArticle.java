package kr.mjc.lsh.basics.jdbc.article.dao;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * 비밀번호 수정
 */
@Slf4j
public class UpdateArticle {

    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDaoImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Update article -  title content articleId userId  : ");
        int updatedRows = articleDao.updateArticle(scanner.next(), scanner.next(),
                scanner.nextInt(), scanner.nextInt());
        if (updatedRows >= 1)
            log.debug("수정 성공");
        else
            log.debug("수정 실패"); // 회원번호가 없거나 비밀번호 틀림
    }
}