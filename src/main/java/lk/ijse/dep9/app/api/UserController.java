package lk.ijse.dep9.app.api;

import lk.ijse.dep9.app.dto.UserDTO;
import lk.ijse.dep9.app.service.custom.UserService;
import lk.ijse.dep9.app.util.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void createNewUserAccount(@Validated(ValidationGroups.Create.class) @RequestBody UserDTO user){
        userService.createNewUserAccount(user);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/me",consumes = "application/json")
    public void updateUserAccountDetails(@Validated(ValidationGroups.Update.class) @RequestBody UserDTO user,
                                         @AuthenticationPrincipal(expression = "username") String username){
//        System.out.println(user);
        user.setUsername(username);
        userService.updateUserAccountDetails(user);

    }

    @GetMapping(value = "/me",produces = "application/json")
    public UserDTO getUserAccountDetails(@AuthenticationPrincipal(expression = "username") String username){
        // stored the username in the request at Security filter
        return userService.getUserAccountDetails(username);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me")
    public void deleteUserAccount(@AuthenticationPrincipal(expression = "username") String username){

        userService.deleteUserAccount(username);

    }

}
