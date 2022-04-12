package kr.mjc.lsh.basics.jdbc.article.dao;

import kr.mjc.lsh.basics.jdbc.DataSourceFactory;
import kr.mjc.lsh.basics.jdbc.DbException;
import kr.mjc.lsh.basics.jdbc.article.Article;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ArticleDaoImpl implements ArticleDao {

    private static final String LIST_ARTICLES =
            "select articleId, title, content, name from article order by articleId desc limit ?,?";

    private static final String GET_ARTICLE =
            "select articleId, title, content, name from article where articleId=?";

    private static final String ADD_ARTICLE =
            "insert article(title, content, name, cdate, udate ) values(?,?,?,current_date ,current_timestamp )";

    private static final String UPDATE_ARTICLE =
            "update article set title=?, udate=current_timestamp  where articleId=? and name=?";

    private static final String DELETE_ARTICLE =
            "delete from article where articleId=? and name=?";

    private final DataSource ds;

    /**
     * 기본 생성자. 데이터소스를 초기화한다.
     */
    public ArticleDaoImpl() {
        ds = DataSourceFactory.getDataSource();
    }

    @Override
    public List<Article> listArticles(int count, int page) {
        int offset = (page - 1) * count;  // 목록의 시작 시점
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(LIST_ARTICLES)) {
            ps.setInt(1, offset);
            ps.setInt(2, count);
            List<Article> articleList = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Article article = new Article();
                article.setArticleId(rs.getInt("articleId"));
                article.setTitle(rs.getString("title"));
                article.setContent(rs.getString("content"));
                article.setName(rs.getString("name"));
                articleList.add(article);
            }
            return articleList;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public Article getArticle(int articleId) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_ARTICLE)) {
            ps.setInt(1, articleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Article article = new Article();
                article.setArticleId(rs.getInt("articleId"));
                article.setName(rs.getString("name"));
                return article;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }


    @Override
    public void addArticle(Article article) throws DbException {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(ADD_ARTICLE,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, article.getTitle());
            ps.setString(2, article.getContent());
            ps.setString(3, article.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                article.setArticleId(rs.getInt("insert_id"));
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public int updateArticle(int articleId, String name,
                              String newTitle) {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_ARTICLE)) {
            ps.setObject(1, newTitle);
            ps.setObject(2, articleId);
            ps.setObject(3, name);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    @Override
    public int deleteArticle(int articleId, String name) throws DbException {
        try (Connection con = ds.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_ARTICLE)) {
            ps.setInt(1, articleId);
            ps.setString(2, name);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }
}