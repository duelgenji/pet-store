package com.knight.petstore.controller;

import com.knight.petstore.data.PetData;
import com.knight.petstore.model.Pet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author knight
 * @since 2023/11/7
 **/
@Tag(name="pet", description = "Everything about your Pets")
@RequestMapping("pet")
@RestController
public class PetController {

    private static PetData petData = new PetData();

    @Operation(summary="新增宠物", description = "新增宠物")
    @PostMapping
    public ResponseEntity<?> addPet(@RequestBody Pet pet) {
        petData.addPet(pet);
        return ResponseEntity.ok("pet added");
    }


    @Operation(summary="删除一个宠物ById", description = "删除指定Id的宠物")
    @DeleteMapping("{petId}")
    public ResponseEntity<?> deletePet(@PathVariable Long petId) {
        if (petId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No petId provided. Try again?");
        }
        petData.deletePetById(petId);

        final Pet pet = petData.getPetById(petId);

        if (null == pet) {
            return ResponseEntity.ok("pet deleted");

        } else {
            return new ResponseEntity<>("Pet couldn't be deleted.", HttpStatus.NOT_MODIFIED);
        }
    }


    @Operation(summary="修改宠物信息", description = "修改已存在的的宠物")
    @PutMapping
    public ResponseEntity<?> updatePet(@RequestBody Pet pet) {

        if (pet.getId() == null || pet.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No petId or Name provided. Try again?");
        }

        final Pet existingPet = petData.getPetById(pet.getId());

        if (existingPet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }

        petData.deletePetById(existingPet.getId());
        petData.addPet(pet);

        return ResponseEntity.ok("pet updated");
    }


    @Operation(summary="表单修改宠物信息", description = "通过表单信息修改已存在的的宠物")
    @PostMapping("{petId}")
    public ResponseEntity<?> updatePetByForm(@PathVariable Long petId,
                                             @RequestParam String name,@RequestParam String status) {

        if (petId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No petId provided. Try again?");
        }

        final Pet existingPet = petData.getPetById(petId);

        if (existingPet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }

        petData.deletePetById(existingPet.getId());
        existingPet.setName(name);
        existingPet.setStatus(status);
        petData.addPet(existingPet);

        return ResponseEntity.ok("pet updated");
    }


    @Operation(summary="上传宠物照片", description = "通过宠物Id上传宠物照片")
    @PostMapping("{petId}/uploadImage")
    public ResponseEntity<?> uploadImage(@PathVariable Long petId,
                                         @RequestParam MultipartFile file) {
        if (petId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No petId provided. Try again?");
        }

        final Pet existingPet = petData.getPetById(petId);

        if (existingPet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }

        existingPet.getPhotoUrls().add(file.getOriginalFilename());
        petData.deletePetById(existingPet.getId());
        petData.addPet(existingPet);

        return ResponseEntity.ok("image uploaded");
    }

    @Operation(summary="获取宠物列表By状态", description = "根据指定的状态获取宠物列表", responses = {
            @ApiResponse(description = "成功返回", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Pet.class)))),
            @ApiResponse(description = "参数错误", responseCode = "400", content = @Content),
            @ApiResponse(description = "资源未找到", responseCode = "404", content = @Content)},
            parameters = {
                    @Parameter(name = "status", schema = @Schema(allowableValues = {"available","pending","sold"}))
            }
    )
    @GetMapping("findByStatus")
    public ResponseEntity<?> findPetsByStatus(
            @RequestParam String status) {

        if (status == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No status provided. Try again?");
        }

        final List<Pet> petByStatus = petData.findPetByStatus(status);

        if (petByStatus == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pets not found");
        }

        return ResponseEntity.ok(petByStatus);
    }

    @Operation(summary="获取宠物信息ById", description = "获取指定Id的宠物信息")
    @GetMapping("{petId}")
    public ResponseEntity<?> getPetById(@PathVariable Long petId) {

        if (petId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No petId provided. Try again?");
        }

        Pet pet = petData.getPetById(petId);

        if (pet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
        }
        return ResponseEntity.ok(pet);

    }


    @Operation(summary="获取宠物列表ByTag", description = "根据指定的标签获取宠物列表")
    @GetMapping("findPetByTag")
    @Deprecated
    public ResponseEntity<?> findPetByTag(@RequestParam String tag) {
        return ResponseEntity.ok().build();
    }

}
