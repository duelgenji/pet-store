package com.knight.petstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "Order", description = "订单")
public class Order {
  private long id;
  private long petId;
  private int quantity;
  private Date shipDate;
  @Schema(description = "订单状态", allowableValues = "placed, approved, delivered")
  private String status;
  private boolean complete;
}