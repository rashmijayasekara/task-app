package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.util.ConnectionUtil;
import lk.ijse.dep9.app.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component

public class ProjectDAOImpl implements ProjectDAO {
    private final Connection connection;
    private JdbcTemplate jdbc;

    public ProjectDAOImpl(Connection connection,JdbcTemplate jdbcTemplate) {
        this.connection = connection;
        this.jdbc=jdbcTemplate;
    }

    @Override
    public Project save(Project project) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement statement = con.prepareStatement("INSERT INTO Project(name, username) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,project.getName());
            statement.setString(2,project.getUsername());
            return statement;
        },keyHolder);
        return project;
    }
//        try {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO Project(name, username) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1, project.getName());
//            statement.setString(2, project.getUsername());
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            generatedKeys.next();
//            project.setId(generatedKeys.getInt(1));
//            return project;
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void update(Project project) {
        jdbc.update("UPDATE Project SET name=? AND username=? WHERE id=?",project.getId());
//        try {
//            PreparedStatement statement = connection.prepareStatement("UPDATE Project SET name=? AND username=? WHERE id=?");
//            statement.setString(1,project.getName());
//            statement.setString(2,project.getUsername());
//            statement.setInt(3,project.getId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public void deleteById(Integer pk) {
        jdbc.update("DELETE FROM Project WHERE id=?",pk);
//        try {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM Project WHERE id=?");
//            statement.setInt(1,pk);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public Optional<Project> findById(Integer pk) {
        return jdbc.query("SELECT name,username FROM Project WHERE id=?",rst->{
            return Optional.of(new Project(rst.getInt("id"),rst.getString("name"),rst.getString("username")));
        },pk);
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT name,username FROM Project WHERE id=?");
//            statement.setInt(1,pk);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()){
//                return Optional.of(new Project(pk,resultSet.getString("name"),resultSet.getString("username")));
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public List<Project> findAll() {
        return jdbc.query("SELECT * FROM Project",(rst,rowIndex)->
            new Project(rst.getInt("id"),rst.getString("name"),rst.getString("username")));
//        List<Project> projectList= new ArrayList<>();
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Project");
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()){
//                projectList.add(new Project(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("username")));
//            }
//            return projectList;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public long count() {
        return jdbc.queryForObject("SELECT COUNT(id) FROM Project",long.class);
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM Project");
//            ResultSet resultSet = statement.executeQuery();
//            return resultSet.getLong(1);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public boolean existsById(Integer pk) {
       return findById(pk).isPresent();
    }

    @Override
    public List<Project> findAllProjectsByUserName(String username) {

        return jdbc.query("SELECT * FROM Project WHERE username=?",(rst,rowIndex)->
            new Project(rst.getInt("id"),rst.getString("name"),rst.getString("username")),username);
//        List<Project> projectList=new ArrayList<>();
//
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Project WHERE username=?");
//            statement.setString(1,username);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()){
//                projectList.add(new Project(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("username")));
//            }
//            return projectList;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
