package lk.ijse.dep9.app.api;

import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @PostMapping
    public void createNewUserAccount(){

    }
    @PatchMapping("/me")
    public void updateUserAccountDetails(){

    }
    @GetMapping("/me")
    public void getUserAccountDetails(){

    }
    @DeleteMapping("/me")
    public void deleteUserAccount(){

    }

}
