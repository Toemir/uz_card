package com.company.controller;

import com.company.dto.CardBalanceDTO;
import com.company.dto.CardCreateAndUpdateDTO;
import com.company.dto.CardDTO;
import com.company.dto.CardStatusDTO;
import com.company.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/card")
@Slf4j
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid CardCreateAndUpdateDTO dto) {
        log.info("Creating a card");
        cardService.create(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change/status")
    public ResponseEntity<Void> changeStatus(@RequestParam("c") String id,
                                             @RequestBody @Valid CardStatusDTO dto) {
        log.info("Changing status of a card");
        cardService.changeStatus(id,dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<CardDTO> getById(@RequestParam("c") String id) {
        log.info("Finding a card");
        return ResponseEntity.ok(cardService.getById(id));
    }

    @GetMapping("/list/phone")
    public ResponseEntity<List<CardDTO>> listByPhone(@RequestParam("phone") String phone) {
        log.info("Finding a card list by phone");
        return ResponseEntity.ok(cardService.listByPhone(phone));
    }

    @GetMapping("/list/client")
    public ResponseEntity<List<CardDTO>> listByClient(@RequestParam("c") String clientId) {
        log.info("Finding a card list by phone");
        return ResponseEntity.ok(cardService.listByClientId(clientId));
    }

    @GetMapping("/number={number}")
    public ResponseEntity<CardDTO> listByNumber(@PathVariable("number") String number) {
        log.info("Finding a card by number");
        return ResponseEntity.ok(cardService.getByNumber(number));
    }

    @GetMapping("/balance/number={number}")
    public ResponseEntity<CardBalanceDTO> getBalanceByNumber(@PathVariable("number") String number) {
        log.info("Finding a card balance by number");
        return ResponseEntity.ok(cardService.getBalanceByNumber(number));
    }
}
