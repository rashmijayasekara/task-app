package lk.ijse.dep9.app.dao.custom.impl;

import lk.ijse.dep9.app.dao.custom.TaskDAO;
import lk.ijse.dep9.app.dao.util.ConnectionUtil;
import lk.ijse.dep9.app.entity.Project;
import lk.ijse.dep9.app.entity.Task;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component

public class TaskDAOImpl implements TaskDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Task> taskRowMapper = (rs, rowNum) -> new Task(rs.getInt("id"), rs.getString("content"), Task.Status.valueOf(rs.getString("status")), rs.getInt("project_id"));
    public TaskDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Task save(Task task) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

      jdbcTemplate.update(con -> {
          PreparedStatement statement = con.prepareStatement("INSERT INTO Task (content, status, project_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
          statement.setString(1,task.getContent());
          statement.setString(2,task.getStatus().toString());
          statement.setInt(3,task.getId());
          return statement;
      },keyHolder);
      task.setId(keyHolder.getKey().intValue());
      return task;


//        try {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO Task (content, status, project_id) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
//            statement.setString(1,task.getContent());
//            statement.setString(2, task.getStatus().toString());
//            statement.setInt(3,task.getProjectId());
//            statement.executeUpdate();
//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            generatedKeys.next();
//            task.setId(generatedKeys.getInt(1));
//            return task;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void update(Task task) {

        jdbcTemplate.update("UPDATE Task SET content=?, status=?, project_id=? WHERE id=?", task.getContent(), task.getStatus().toString(), task.getProjectId(), task.getId());

       // jdbcTemplate.update("UPDATE Task SET content=?, status=?,project_id=? WHERE id=?",task.getContent(),task.getStatus().toString(),task.getProjectId(),task.getId());
//        try {
//            PreparedStatement statement = connection.prepareStatement("UPDATE Task SET content=?, status=?,project_id=? WHERE id=?");
//            statement.setString(1,task.getContent());
//            statement.setString(2, task.getStatus().toString());
//            statement.setInt(3,task.getProjectId());
//            statement.setInt(4,task.getId());
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }


    }

    @Override
    public void deleteById(Integer pk) {
        jdbcTemplate.update("DELETE FROM Task WHERE id=?",pk);
//        try {
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM Task WHERE id=?");
//            statement.setInt(1,pk);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public Optional<Task> findById(Integer pk) {

        return jdbcTemplate.query("SELECT * FROM Task WHERE id=?", taskRowMapper, pk).stream().findFirst();


//        return Optional.ofNullable(jdbcTemplate.query("SELECT project_id,content,status FROM Task WHERE id=?",rst->{
//           return new Task(rst.getInt("id"),rst.getString("content"),Task.Status.valueOf(rst.getString("status")),rst.getInt("project_id"));
//        },pk));
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT project_id,content,status FROM Task WHERE id=?");
//            statement.setInt(1,pk);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()){
//                return Optional.of(new Task(resultSet.getInt("id"),resultSet.getString("content"),Task.Status.valueOf(resultSet.getString("status")),resultSet.getInt("project_id")));
//            }
//            return Optional.empty();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public List<Task> findAll() {
        return jdbcTemplate.query("SELECT * FROM Task", taskRowMapper);
//        jdbcTemplate.query("SELECT * FROM Task",(rs, rowNum) -> new Task(rs.getInt("id"),rs.getString("content"),Task.Status.valueOf(rs.getString("status"),rs.getString("project_id"))));
//        List<Task> taskList=new ArrayList<>();
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Task");
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()){
//                taskList.add(new Task(resultSet.getInt("id"),resultSet.getString("content"),Task.Status.valueOf(resultSet.getString("status")),resultSet.getInt("project_id")));
//            }
//            return taskList;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(id) FROM Task", Long.class);
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM Task");
//            ResultSet resultSet = statement.executeQuery();
//            resultSet.next();
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
    public List<Task> findAllTaskByProjectId(Integer projectId) {

        return jdbcTemplate.query("SELECT * FROM Task WHERE project_id = ?", taskRowMapper, projectId);

//        return jdbcTemplate.query("SELECT * FROM Task WHERE project_id=?",(rst,rowIndex)->{
//            return new Task(rst.getInt("id"),rst.getString("content"), Task.Status.valueOf(rst.getString("status"),rst.getString("project_id")));
//        },projectId);
//        List<Task> taskList=new ArrayList<>();
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Task WHERE project_id=?");
//            statement.setInt(1,projectId);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()){
//                taskList.add(new Task(resultSet.getInt("id"),resultSet.getString("content"),Task.Status.valueOf(resultSet.getString("status")),resultSet.getInt("project_id")));
//            }
//            return taskList;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    }
}
