package com.company.controller;

import com.company.dto.ClientCreateOrUpdateDTO;
import com.company.dto.ClientDTO;
import com.company.dto.ClientFilterDTO;
import com.company.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/client")
@Slf4j
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid ClientCreateOrUpdateDTO dto) {
        log.info("Creating a client");
        clientService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestParam("c") String id,
                                       @RequestBody @Valid ClientCreateOrUpdateDTO dto) {
        log.info("Updating a client");
        clientService.update(id,dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pagination/filter")
    public ResponseEntity<List<ClientDTO>> paginationWithFilter(@RequestParam("size") int size,
                                                     @RequestParam("page") int page,
                                                     @RequestBody ClientFilterDTO dto) {
        log.info("Client pagination with filter");
        return ResponseEntity.ok(clientService.paginationWithFilter(size,page,dto));
    }

    @GetMapping("")
    public ResponseEntity<ClientDTO> paginationWithFilter(@RequestParam("c") String id) {
        log.info("Getting a client by id");
        return ResponseEntity.ok(clientService.getById(id));
    }
}
