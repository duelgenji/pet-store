package com.knight.petstore.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author knight
 * @since 2023/11/7
 **/
@Data
@Schema(description = "宠物")
public class Pet {

    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    @Schema(description = "pet status in the store", allowableValues = "available,pending,sold")
    private String status;

}
