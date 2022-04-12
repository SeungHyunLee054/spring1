package kr.mjc.lsh.basics.jdbc.article.dao;

import kr.mjc.lsh.basics.jdbc.DbException;
import kr.mjc.lsh.basics.jdbc.article.Article;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class AddArticle {

    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDaoImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Insert - title content name : ");
        Article article = new Article();
        article.setTitle(scanner.next());
        article.setContent(scanner.next());
        article.setName(scanner.next());
        scanner.close();

        try {
            articleDao.addArticle(article);
            log.debug(article.toString());
        } catch (DbException e) {
            log.error(e.toString());
        }
    }
}