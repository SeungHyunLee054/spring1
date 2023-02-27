package kr.mjc.lsh.basics.jdbc.article.dao;

import kr.mjc.lsh.basics.jdbc.DbException;
import kr.mjc.lsh.basics.jdbc.article.Article;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class AddArticle {

    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDaoImpl();
        Scanner scanner = new Scanner(System.in).useDelimiter("//");
        System.out.print("Insert - title// : ");
        String title = scanner.next();
        System.out.print("Insert - content// : ");
        String content = scanner.next();
        scanner.close();

        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setName("lsh");
        articleDao.addArticle(article);

        log.debug("저장 완료");
    }
}