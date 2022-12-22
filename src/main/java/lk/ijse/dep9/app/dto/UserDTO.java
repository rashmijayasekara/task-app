package lk.ijse.dep9.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.ijse.dep9.app.util.ValidationGroups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = "password",allowSetters = true)

public class UserDTO implements Serializable {
    @NotBlank(message = "full name can't be empty or null")
    @Pattern(regexp = "^[A-Za-z ]+$")
    private String fullName;
    @NotBlank(message = "username can't be empty or null",groups = ValidationGroups.Create.class)
    private String username;
    @NotEmpty(message = "password can't be empty")
    @Length(min = 3,message = "Password Should be at least 3 characters")
    private String password;
}
