package com.knight.petstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "宠物标签")
public class Tag {

  @Schema(description = "标签id")
  private long id;
  @Schema(description = "标签名")
  private String name;

}