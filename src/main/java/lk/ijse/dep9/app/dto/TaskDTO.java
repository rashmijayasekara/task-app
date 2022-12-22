package lk.ijse.dep9.app.dto;

import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
public class TaskDTO implements Serializable {
    @Null(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "Task ID can't be specified")
    private Integer id;
    @NotBlank(message = "Task content can't be empty or null")  // compulsory to have
    private String content;
    @Null(groups = {ValidationGroups.Create.class,ValidationGroups.Update.class},message = "Task ID can't be specified")
    private Integer projectId;


}
