package lk.ijse.dep9.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data @AllArgsConstructor @NoArgsConstructor @Entity
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String content;
    @Enumerated(EnumType.STRING)
    private Status status;
    @JoinColumn(name = "project_id",referencedColumnName = "id",nullable = false)
    @ManyToOne
    private Project project;

    public static enum Status{
        COMPLETED, NOT_COMPLETED
    }

    public Task(String content, Status status, Project project) {
        this.content = content;
        this.status = status;
        this.project = project;
    }
}
