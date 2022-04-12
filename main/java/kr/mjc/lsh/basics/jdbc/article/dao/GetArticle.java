package kr.mjc.lsh.basics.jdbc.article.dao;

import kr.mjc.lsh.basics.jdbc.article.Article;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class GetArticle {
    public static void main(String[] args) {
        ArticleDao articleDao = new ArticleDaoImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Get - articleId : ");
        Article article = articleDao.getArticle(scanner.nextInt());
        if (article != null)
            log.debug(article.toString());
        else
            log.debug("회원 없음");
    }
}