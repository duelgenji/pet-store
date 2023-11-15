package com.knight.petstore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "宠物类别")
public class Category {

  @ApiModelProperty(name = "id", notes = "分类id")
  private long id;

  @ApiModelProperty(notes = "分类名")
  private String name;

}