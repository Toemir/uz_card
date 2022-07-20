package com.company.controller;

import com.company.dto.ProfileCreateAndUpdateDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.ProfileFilterDTO;
import com.company.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid ProfileCreateAndUpdateDTO dto) {
        log.info("Creating a profile");
        profileService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("p") String profileId,
                                       @RequestBody @Valid ProfileCreateAndUpdateDTO dto) {
        log.info("Updating a profile");
        profileService.update(profileId,dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagination/filter")
    public ResponseEntity<List<ProfileDTO>> paginationWithFilter(@RequestParam("size") int size,
                                                                 @RequestParam("page") int page,
                                                                 @RequestBody ProfileFilterDTO dto) {
        log.info("Getting a profile Pagination with filter");
        return ResponseEntity.ok(profileService.paginationWithFilter(size,page,dto));
    }
}
