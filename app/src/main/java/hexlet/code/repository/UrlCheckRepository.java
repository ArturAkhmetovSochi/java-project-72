package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;


import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class UrlCheckRepository extends BaseRepository {

    public static void save(UrlCheck urlCheck, Url url) throws SQLException {
        String sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, urlCheck.getStatusCode());
            preparedStatement.setString(2, urlCheck.getTitle());
            preparedStatement.setString(3, urlCheck.getH1());
            preparedStatement.setString(4, urlCheck.getDescription());
            preparedStatement.setLong(5, url.getId());
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
                urlCheck.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            } else {
                throw new SQLException("DB have not returned an id or createdAt after saving an entity");
            }
        }
    }

    public static List<UrlCheck> findByUrlId(Long id) throws SQLException {
        String sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY created_at DESC";
        var listOfChecks = new LinkedList<UrlCheck>();
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var resultStatusCode = resultSet.getInt("status_code");
                var resultTitle = resultSet.getString("title");
                var resultH1 = resultSet.getString("h1");
                var resultDescription = resultSet.getString("description");
                var resultCreatedAt = resultSet.getTimestamp("created_at");
                var resultId = resultSet.getLong("id");
                var resultUrlId = resultSet.getLong("url_id");
                UrlCheck urlCheck = new UrlCheck(resultStatusCode,
                        resultTitle,
                        resultH1,
                        resultDescription,
                        resultUrlId);
                urlCheck.setId(resultId);
                urlCheck.setCreatedAt(resultCreatedAt);
                listOfChecks.add(urlCheck);
            }
            return listOfChecks;
        }
    }

    public static List<UrlCheck> getEntities() throws SQLException {
        String sql = "SELECT * FROM url_checks GROUP BY url_id ORDER BY created_at DESC";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var result = new LinkedList<UrlCheck>();
            while (resultSet.next()) {
                int statusCode = resultSet.getInt("status_code");
                String title = resultSet.getString("title");
                String h1 = resultSet.getString("h1");
                String description = resultSet.getString("description");
                Long urlId = resultSet.getLong("url_id");
                UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, urlId);
                urlCheck.setId(resultSet.getLong("id"));
                urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
                urlCheck.setUrlId(resultSet.getLong("url_id"));
                result.add(urlCheck);
            }
            return result;
        }
    }

    public static List<UrlCheck> getChecksUrl() throws SQLException {
        String sql = "SELECT DISTINCT ON (url_id) * FROM url_checks ORDER BY url_id, created_at DESC";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var result = new ArrayList<UrlCheck>();
            while (resultSet.next()) {
                int statusCode = resultSet.getInt("status_code");
                String title = resultSet.getString("title");
                String h1 = resultSet.getString("h1");
                String description = resultSet.getString("description");
                Long urlId = resultSet.getLong("url_id");
                UrlCheck urlCheck = new UrlCheck(statusCode, title, h1, description, urlId);
                urlCheck.setId(resultSet.getLong("id"));
                urlCheck.setCreatedAt(resultSet.getTimestamp("created_at"));
                result.add(urlCheck);
            }
            return result;
        }
    }
}
