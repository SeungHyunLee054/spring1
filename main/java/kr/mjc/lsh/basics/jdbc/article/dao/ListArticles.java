package kr.mjc.lsh.basics.jdbc.article.dao;

import kr.mjc.lsh.basics.jdbc.article.Article;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class ListArticles {
    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDaoImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.print("List - count page : ");
        List<Article> articleList =
                articleDao.listArticles(scanner.nextInt(), scanner.nextInt());
        log.debug(articleList.toString());
    }
}