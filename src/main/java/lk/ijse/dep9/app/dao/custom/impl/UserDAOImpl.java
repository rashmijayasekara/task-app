package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.UserDAO;
import lk.ijse.dep9.app.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component

public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbc;
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(rs.getString("username"), rs.getString("password"), rs.getString("full_name"));

    public UserDAOImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User save(User user) {
        jdbc.update("INSERT INTO User (username, full_name, password) VALUES (?, ?, ?)", user.getUsername(), user.getFullName(), user.getPassword());
        return user;

//        jdbc.update("INSERT INTO User(username, full_name, password) VALUES (?,?,?)", user.getUsername(),user.getFullName(),user.getPassword());
//        return user;
//        try {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO User(username, full_name, password) VALUES (?,?,?)");
//            statement.setString(1, user.getUsername());
//            statement.setString(2, user.getFullName());
//            statement.setString(3, user.getPassword());
//            statement.executeUpdate();
//            return user;
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public void update(User user) {
        jdbc.update("UPDATE User SET full_name=?, password=? WHERE username=?", user.getFullName(), user.getPassword(), user.getUsername());

//        jdbc.update("UPDATE User SET full_name=?, password=? WHERE username=?",user.getFullName(),user.getPassword(),user.getUsername());
//
//        try {
//            PreparedStatement statement = connection.prepareStatement();
//            statement.setString(1,user.getFullName());
//            statement.setString(2,user.getPassword());
//            statement.setString(3,user.getUsername());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public void deleteById(String username) {
        jdbc.update("DELETE FROM User WHERE username=?",username);
//        try {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM User WHERE username=?");
//            statement.setString(1,username);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public Optional<User> findById(String username) {

        return jdbc.query("SELECT * FROM User WHERE username=?", userRowMapper, username).stream().findFirst();

        // return Optional.ofNullable(jdbc.queryForObject("SELECT full_name,password FROM User WHERE username=?",(rs, rowNum) -> new User(rs.getString("username"),rs.getString("password"),rs.getString("full_name")),username));
//        return jdbc.query("SELECT full_name,password FROM User WHERE username=?",rst -> {
//            if (rst.next()){
//                return Optional.of(new User(username,rst.getString("password"),rst.getString("full_name")));
//            }else {
//                return Optional.empty();
//            }
//        },username);
//        try {
//            PreparedStatement statement = connection.prepareStatement();
//            statement.setString(1,username);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()){
//                return Optional.of(new User(username, resultSet.getString("password"),resultSet.getString("full_name")));
//            }
//            return Optional.empty();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public List<User> findAll() {
        return jdbc.query("SELECT * FROM User", userRowMapper);
//        return jdbc.query("SELECT * FROM User",(rs, rowNum) ->
//            new User(rs.getString("username"),rs.getString("password"),rs.getString("full_name")));

//        return jdbc.query("SELECT * FROM User",rst->{
//            List<User> userList=new ArrayList<>();
//            while (rst.next()){
//                userList.add(new User(rst.getString("username"),rst.getString("password"),rst.getString("full_name")));
//            }
//            return userList;
//        });




//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM User");
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()){
//                userList.add(new User(resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("full_name")));
//            }
//            return userList;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public long count() {

        return jdbc.queryForObject("SELECT COUNT(username) FROM User", Long.class);
//        return jdbc.queryForObject("SELECT COUNT(username) FROM User",long.class);

//        return jdbc.query("SELECT COUNT(username) FROM User", rst->{
//            rst.next();
//            return rst.getLong(1);
//        });
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(username) FROM User");
//            ResultSet resultSet = statement.executeQuery();
//            resultSet.next();
//            return  resultSet.getLong(1);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public boolean existsById(String username) {
           return findById(username).isPresent();
    }
}
