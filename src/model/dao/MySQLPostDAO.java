package model.dao;

import model.ModelException;
import model.entities.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MySQLPostDAO implements PostDAO {
    @Override
    public boolean save(Post post) throws ModelException {
        String sqlInsert = "INSERT INTO posts (content, date) VALUES (?, ?);";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sqlInsert);

        dbHandler.setString(1, post.getContent());
        dbHandler.setDate(2, new Date(post.getDate().getTime()));

        int rowsAffected = dbHandler.executeUpdate();

        dbHandler.close();

        return rowsAffected > 0;
    }

    @Override
    public List<Post> listAll() throws ModelException {
        List<Post> posts = new ArrayList<>();

        String sqlQuery = "SELECT * FROM posts;";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.statement();

        dbHandler.executeQuery(sqlQuery);

        while (dbHandler.next()) {
            int postId = dbHandler.getInt("id");
            String content = dbHandler.getString("content");
            Date date = dbHandler.getDate("date");

            Post post = new Post(postId);
            post.setContent(content);
            post.setDate(date);

            posts.add(post);
        }

        dbHandler.close();

        return posts;
    }

    @Override
    public boolean update(Post post) throws ModelException {
        String sqlUpdate = "UPDATE posts SET content = ?, date = ? WHERE id = ?;";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sqlUpdate);

        dbHandler.setString(1, post.getContent());
        dbHandler.setDate(2, new Date(post.getDate().getTime()));
        dbHandler.setInt(3, post.getId());

        int rowsAffected = dbHandler.executeUpdate();

        dbHandler.close();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Post post) throws ModelException {
        String sqlDelete = "DELETE FROM posts WHERE id = ?;";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sqlDelete);

        dbHandler.setInt(1, post.getId());

        int rowsAffected = dbHandler.executeUpdate();

        dbHandler.close();

        return rowsAffected > 0;
    }

    @Override
    public Post findById(int id) throws ModelException {
        String sql = "SELECT * FROM posts WHERE id = ?;";

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.prepareStatement(sql);
        dbHandler.setInt(1, id);

        dbHandler.executeQuery();
        Post post = null;
        while (dbHandler.next()) {
            int postId = dbHandler.getInt("id");
            String content = dbHandler.getString("content");
            Date date = dbHandler.getDate("date");

            post = new Post(postId);
            post.setContent(content);
            post.setDate(date);
            break;
        }

        dbHandler.close();

        return post;
    }
}
