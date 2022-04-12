package kr.mjc.lsh.basics.jdbc.article.dao;

import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class DeleteArticle {

    public static void main(String[] args) {
        ArticleDao userDao = new ArticleDaoImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Delete - articleId name : ");

        int updatedRows = userDao.deleteArticle(scanner.nextInt(), scanner.next());
        if (updatedRows >= 1)
            log.debug("삭제 완료");
        else
            log.debug("삭제 실패");
    }
}