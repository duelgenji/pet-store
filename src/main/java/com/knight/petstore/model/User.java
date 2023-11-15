package com.knight.petstore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "User", description = "用户")
public class User {
  private long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String phone;
  @ApiModelProperty(name = "User Status", allowableValues = "1-registered,2-active,3-closed")
  private int userStatus;

}