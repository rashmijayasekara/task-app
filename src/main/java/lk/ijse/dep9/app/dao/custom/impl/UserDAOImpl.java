package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.dao.util.ConnectionUtil;
import lk.ijse.dep9.app.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Scope("request")
public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    public UserDAOImpl() {
        this.connection = ConnectionUtil.getConnection();
    }

    @Override
    public User save(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO User(username, full_name, password) VALUES (?,?,?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            return user;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE User SET full_name=?, password=? WHERE username=?");
            statement.setString(1,user.getFullName());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getUsername());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM User WHERE username=?");
            statement.setString(1,username);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> findById(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT full_name,password FROM User WHERE username=?");
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return Optional.of(new User(username, resultSet.getString("password"),resultSet.getString("full_name")));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList=new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                userList.add(new User(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("full_name")));
            }
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public long count() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(username) FROM User");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return  resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(String username) {
           return findById(username).isPresent();
    }
}
