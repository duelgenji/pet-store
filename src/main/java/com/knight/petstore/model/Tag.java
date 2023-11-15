package com.knight.petstore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "宠物标签")
public class Tag {

  @ApiModelProperty(name = "标签id")
  private long id;
  @ApiModelProperty(name = "标签名")
  private String name;

}