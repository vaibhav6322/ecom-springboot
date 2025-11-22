package com.ecom.app.controller;

import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import com.ecom.app.model.User;
import com.ecom.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserController {

//    @Autowired
//    private UserService userService;

//    private UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    private final UserService userService;

    @GetMapping("api/users")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
//        return ResponseEntity.ok(userService.fetchAllUsers());
        return new ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
    }

//    @PostMapping("api/users")
    @RequestMapping(value = "/api/users",method = RequestMethod.POST)
    public ResponseEntity<String> createUsers(@RequestBody UserRequest UserRequest)
    {
        userService.addUsers(UserRequest);
        return new ResponseEntity<>("Successfully added",HttpStatus.OK);
    }
    @GetMapping("api/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
    {
        Optional<UserResponse> userOpt = userService.getUserById(id);

        return userOpt
                .map(ResponseEntity::ok)               // 200 + body if present
                .orElseGet(() -> ResponseEntity.notFound().build()); // 404 if not
    }
    @PostMapping("api/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserRequest updateUserRequest)
    {
       boolean updated = userService.updateUserById(id,updateUserRequest);
       if(updated!=false)
       {
           return ResponseEntity.ok("updated");
       }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found");
    }
}
