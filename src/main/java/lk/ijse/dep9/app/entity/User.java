package lk.ijse.dep9.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable, SuperEntity {

    private String username;
    private String password;
    private String fullName;

}
