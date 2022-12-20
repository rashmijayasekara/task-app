package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.entity.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDAOImpl implements TaskDAO {
    private final Connection connection;

    public TaskDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Task save(Task task) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Task (content, status, project_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,task.getContent());
            statement.setString(2, task.getStatus().toString());
            statement.setInt(3,task.getProjectId());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            task.setId(generatedKeys.getInt(1));
            return task;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Task task) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Task SET content=?, status=?,project_id=? WHERE id=?");
            statement.setString(1,task.getContent());
            statement.setString(2, task.getStatus().toString());
            statement.setInt(3,task.getProjectId());
            statement.setInt(4,task.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void deleteById(Integer pk) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Task WHERE id=?");
            statement.setInt(1,pk);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<Task> findById(Integer pk) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT project_id,content,status FROM Task WHERE id=?");
            statement.setInt(1,pk);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return Optional.of(new Task(resultSet.getInt("id"),resultSet.getString("content"),Task.Status.valueOf(resultSet.getString("status")),resultSet.getInt("project_id")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> taskList=new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Task");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                taskList.add(new Task(resultSet.getInt("id"),resultSet.getString("content"),Task.Status.valueOf(resultSet.getString("status")),resultSet.getInt("project_id")));
            }
            return taskList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM Task");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean existsById(Integer pk) {
       return findById(pk).isPresent();
    }
}
