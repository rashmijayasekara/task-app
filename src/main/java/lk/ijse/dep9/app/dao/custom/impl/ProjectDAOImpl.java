package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.ProjectDAO;
import lk.ijse.dep9.app.dao.util.ConnectionUtil;
import lk.ijse.dep9.app.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component

public class ProjectDAOImpl implements ProjectDAO {
    @Autowired
    private Connection connection;

    public ProjectDAOImpl() {
        this.connection = ConnectionUtil.getConnection();
    }

    @Override
    public Project save(Project project) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Project(name, username) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, project.getName());
            statement.setString(2, project.getUsername());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            project.setId(generatedKeys.getInt(1));
            return project;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Project project) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Project SET name=? AND username=? WHERE id=?");
            statement.setString(1,project.getName());
            statement.setString(2,project.getUsername());
            statement.setInt(3,project.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteById(Integer pk) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Project WHERE id=?");
            statement.setInt(1,pk);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Project> findById(Integer pk) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT name,username FROM Project WHERE id=?");
            statement.setInt(1,pk);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return Optional.of(new Project(pk,resultSet.getString("name"),resultSet.getString("username")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Project> findAll() {
        List<Project> projectList= new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Project");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                projectList.add(new Project(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("username")));
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM Project");
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(Integer pk) {
       return findById(pk).isPresent();
    }

    @Override
    public List<Project> findAllProjectsByUserName(String username) {
        List<Project> projectList=new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Project WHERE username=?");
            statement.setString(1,username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                projectList.add(new Project(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("username")));
            }
            return projectList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
