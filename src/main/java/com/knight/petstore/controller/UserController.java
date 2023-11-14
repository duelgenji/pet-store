package com.knight.petstore.controller;

import com.knight.petstore.data.UserData;
import com.knight.petstore.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author knight
 * @since 2023/11/8
 **/
@Tag(name="user", description = "Operations about user")
@RequestMapping("user")
@RestController
public class UserController {

    private static UserData userData = new UserData();

    @Operation(summary="新增用户", description = "新增一个用户")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userData.addUser(user);
        return ResponseEntity.ok("user added");
    }

    @Operation(summary="批量新增用户", description = "通过数组批量新增用户")
    @PostMapping("createWithList")
    public ResponseEntity<?> createUsersWithListInput(@RequestBody List<User> userList) {

        if(userList == null || userList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No User provided. Try again?");
        }
        for (User user : userList) {
            userData.addUser(user);
        }
        return ResponseEntity.ok("user added");
    }


    @Operation(summary="删除一个用户By username", description = "删除指定username的用户")
    @DeleteMapping("{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No username provided. Try again?");
        }

        userData.deleteUser(username);

        final User user = userData.findUserByName(username);

        if (null == user) {
            return ResponseEntity.ok("user deleted");

        } else {
            return new ResponseEntity<>("User couldn't be deleted.", HttpStatus.NOT_MODIFIED);

        }
    }


    @Operation(summary="修改用户信息", description = "修改已存在的的用户")
    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
                                        @RequestBody User user) {

        if (username == null || user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Username or user provided. Try again?");
        }

        final User existingUser = userData.findUserByName(username);

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        userData.deleteUser(existingUser.getUsername());
        userData.addUser(user);

        return ResponseEntity.ok("user updated");
    }


    @Operation(summary="获取用户信息By username", description = "获取指定username的用户信息")
    @GetMapping("{username}")
    public ResponseEntity<?> getUserByName(@PathVariable String username) {

        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No username provided. Try again?");
        }

        User user = userData.findUserByName(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);

    }

    @Operation(summary="用户登录", description = "用户登录")
    @GetMapping("login")
    public ResponseEntity<?> loginUser(@RequestParam String username,
                                       @RequestParam String password) {
        Date date = new Date(System.currentTimeMillis() + 3600000);

        return ResponseEntity.ok()
                .header("X-Rate-Limit", String.valueOf(5000))
                .header("X-Expires-After", date.toString()).body("Logged in user session: " + RandomUtils.nextLong());

    }

    @Operation(summary="用户登出", description = "用户登出")
    @GetMapping("logout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().body("logout");
    }

}
