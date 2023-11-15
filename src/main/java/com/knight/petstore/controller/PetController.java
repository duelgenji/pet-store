package com.knight.petstore.controller;

import com.knight.petstore.data.PetData;
import com.knight.petstore.model.Pet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author knight
 * @since 2023/11/7
 **/
@Api(value="pet", description = "Everything about your Pets")
@RequestMapping("pet")
@RestController
public class PetController {

    private static PetData petData = new PetData();

    @ApiOperation(value="新增宠物", notes = "新增宠物")
    @PostMapping
    public ResponseEntity<?> addPet(@RequestBody Pet pet) {
        petData.addPet(pet);
        return ResponseEntity.ok("pet added");
    }


    @ApiOperation(value="删除一个宠物ById", notes = "删除指定Id的宠物")
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


    @ApiOperation(value="修改宠物信息", notes = "修改已存在的的宠物")
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


    @ApiOperation(value="表单修改宠物信息", notes = "通过表单信息修改已存在的的宠物")
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


    @ApiOperation(value="上传宠物照片", notes = "通过宠物Id上传宠物照片")
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

    @ApiOperation(value="获取宠物列表By状态", notes = "根据指定的状态获取宠物列表")
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

    @ApiOperation(value="获取宠物信息ById", notes = "获取指定Id的宠物信息")
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


    @ApiOperation(value="获取宠物列表ByTag", notes = "根据指定的标签获取宠物列表")
    @GetMapping("findPetByTag")
    @Deprecated
    public ResponseEntity<?> findPetByTag(@RequestParam String tag) {
        return ResponseEntity.ok().build();
    }

}
