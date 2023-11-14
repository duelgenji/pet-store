package com.knight.petstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "宠物类别")
public class Category {

  @Schema(name = "id", description = "分类id")
  private long id;

  @Schema(description = "分类名")
  private String name;

}