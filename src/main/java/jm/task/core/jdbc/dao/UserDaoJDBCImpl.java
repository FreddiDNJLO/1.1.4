package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn;

    {
        try {
            conn = Util.getMySQLConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            String sql = "CREATE TABLE Users ("
                    + "id INT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(45) NOT NULL,"
                    + "lastName VARCHAR(45) NOT NULL,"
                    + "age INT NOT NULL,"
                    + "PRIMARY KEY (id))";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("error create");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT  * FROM USERS")) {
            if (resultSet.next()) {
                statement.executeUpdate("DROP TABLE USERS");
            } else {
                System.out.println("Table est`");
            }
        } catch (SQLException e) {
            System.out.println("error drop");
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement("INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error save");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM Users WHERE ID = " + id);
        } catch (SQLException e) {
            System.out.println("error remove");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

            while (resultSet.next()) {
                User user = new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                list.add(user);
            }

        } catch (SQLException e) {
            System.out.println("error getAll");
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Users");
        } catch (SQLException e) {
            System.out.println("error clean");
        }
    }
}