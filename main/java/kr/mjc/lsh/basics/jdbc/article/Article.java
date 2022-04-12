package kr.mjc.lsh.basics.jdbc.article;

import lombok.Data;

@Data
public class Article {
    int articleId;
    String title;
    String content;
    String name;
    String cdate;
    String udate;

    @Override
    public String toString() {
        return String.format("\nArticle{articleId=%d, title=%s, content=%s, name=%s, current date=%s, upload date=%s}", articleId, title, content, name, cdate, udate);
    }
}