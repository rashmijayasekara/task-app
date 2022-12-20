package lk.ijse.dep9.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor
public class Task implements Serializable {

    private int id;
    private String content;
    private Status status;
    private int projectId;
    public enum Status{
        COMPLETED, NOT_COMPLETED
    }

    public Task(String content, Status status, int projectId) {
        this.content = content;
        this.status = status;
        this.projectId = projectId;
    }
}
