package com.company.controller;

import com.company.dto.CompanyCreateAndUpdateDTO;
import com.company.dto.CompanyDTO;
import com.company.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid CompanyCreateAndUpdateDTO dto) {
        companyService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("c") String companyId,
                                       @RequestBody @Valid CompanyCreateAndUpdateDTO dto) {
        companyService.update(companyId,dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagination")
    public ResponseEntity<List<CompanyDTO>> update(@RequestParam("size") int size,
                                                   @RequestParam("page") int page) {
        return ResponseEntity.ok(companyService.pagination(size,page));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> update(@RequestParam("c") String companyId) {
        companyService.delete(companyId);
        return ResponseEntity.ok().build();
    }
}
