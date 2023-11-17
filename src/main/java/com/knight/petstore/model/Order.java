package com.knight.petstore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "Order", description = "订单")
public class Order {
  private long id;
  private long petId;
  private int quantity;
  private Date shipDate;
  @ApiModelProperty(notes = "订单状态", allowableValues = "placed, approved, delivered")
  private String status;
  private boolean complete;
}