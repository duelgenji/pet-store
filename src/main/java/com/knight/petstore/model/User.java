package com.knight.petstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "User", description = "用户")
public class User {
  private long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String phone;
  @Schema(description = "User Status", allowableValues = "1-registered,2-active,3-closed")
  private int userStatus;

}