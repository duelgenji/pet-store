package com.knight.petstore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author knight
 * @since 2023/11/7
 **/

@Data
@ApiModel(description = "宠物")
public class Pet {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    @ApiModelProperty(name = "pet status in the store", allowableValues = "available,pending,sold")
    private String status;

}
