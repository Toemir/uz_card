package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.CardBalanceDTO;
import com.company.dto.CardCreateAndUpdateDTO;
import com.company.dto.CardDTO;
import com.company.dto.CardStatusDTO;
import com.company.entity.CardEntity;
import com.company.enums.ProfileStatus;
import com.company.exceptions.BadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.exceptions.MethodNotAllowedException;
import com.company.exceptions.ProfileNotFoundException;
import com.company.repository.CardRepository;
import com.company.util.CardNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class CardService {
    private final CardRepository cardRepository;

    private final CardNumberGenerator cardNumberGenerator;

    private final ProfileService profileService;

    private final ClientService clientService;

    public CardService(CardRepository cardRepository, CardNumberGenerator cardNumberGenerator, ProfileService profileService, ClientService clientService) {
        this.cardRepository = cardRepository;
        this.cardNumberGenerator = cardNumberGenerator;
        this.profileService = profileService;
        this.clientService = clientService;
    }

    public void create(CardCreateAndUpdateDTO dto) {
        if (cardRepository.existsByProfileIdAndCompanyIdAndVisibleTrue(
                dto.getProfileId(),dto.getCompanyId())) {
            log.error("This client already has a card from this bank");
            throw new BadRequestException(
                    "This client already has a card from this bank"
            );
        }

        CardEntity entity = toEntity(dto);

        cardRepository.save(entity);
    }

    public void changeStatus(String id, CardStatusDTO dto) {
        CustomUserDetails currentUser = profileService.getCurrentUser();

        if (!currentUser.getAuthorities().contains("BANK") &&
              dto.getStatus().equals(ProfileStatus.ACTIVE)) {
            log.warn("Method not allowed");
            throw new MethodNotAllowedException(
                    "Method not allowed"
            );
        }

        cardRepository.changeStatus(dto.getStatus(),id);
    }

    public CardDTO getById(String id) {
        CustomUserDetails currentUser = profileService.getCurrentUser();

        Optional<CardEntity> optional;

        if (currentUser.getAuthorities().contains("PAYMENT")) {
            optional = cardRepository.findByIdAndVisibleTrue(id);
        } else {
            optional = cardRepository.findByIdAndAndCompanyIdAndVisibleTrue(id,currentUser.getId());
        }

        if (optional.isEmpty()) {
            log.error("Card not found");
            throw new ItemNotFoundException(
                    "Card not found"
            );
        }

        CardEntity entity = optional.get();

        return toDTO(entity);
    }

    private CardDTO toDTO(CardEntity entity) {
        CardDTO dto = new CardDTO();
        dto.setId(entity.getId());
        dto.setNumber(entity.getNumber());
        dto.setBalance(entity.getBalance());
        dto.setCompanyId(entity.getCompanyId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setExpiredDate(entity.getExpiredDate());
        dto.setPhone(entity.getPhone());

        return dto;
    }

    private CardEntity toEntity(CardCreateAndUpdateDTO dto) {
        CardEntity entity = new CardEntity();
        entity.setNumber(cardNumberGenerator.generate("12345678",16));
        entity.setBalance(dto.getBalance());
        entity.setProfileId(dto.getProfileId());
        entity.setCompanyId(dto.getCompanyId());

        return entity;
    }

    public List<CardDTO> listByPhone(String phone) {
        List<CardEntity> list = cardRepository.findAllByPhone(phone);

        if (list.size() == 0) {
            log.error("No cards found with this phone");
            throw new ItemNotFoundException(
                    "No cards found with this phone"
            );
        }

        return toResponseList(list);
    }

    private List<CardDTO> toResponseList(List<CardEntity> list) {
        List<CardDTO> dtoList = new LinkedList<>();

        list.forEach(entity -> {
            CardDTO dto = toDTO(entity);

            dtoList.add(dto);
        });

        return dtoList;
    }

    public List<CardDTO> listByClientId(String clientId) {
        if (!clientService.exists(clientId)) {
            log.error("Client not found!");
            throw new ProfileNotFoundException(
                    "Client not found!"
            );
        }

        List<CardEntity> list = cardRepository.findAllByClientId(clientId);

        if (list.size() == 0) {
            log.error("No cards found with this client");
            throw new ItemNotFoundException(
                    "No cards found with this client"
            );
        }

        return toResponseList(list);
    }

    public CardDTO getByNumber(String number) {
        CustomUserDetails currentUser = profileService.getCurrentUser();

        Optional<CardEntity> optional;

        if (currentUser.getAuthorities().contains("PAYMENT")) {
            optional = cardRepository.findByNumberAndVisibleTrue(number);
        } else {
            optional = cardRepository.
                    findByNumberAndCompanyIdAndVisibleTrue(
                            number,currentUser.getId());
        }

        if (optional.isEmpty()) {
            log.error("Card not found!");
            throw new ItemNotFoundException(
                    "Card not found!"
            );
        }

        CardEntity entity = optional.get();

        return toDTO(entity);
    }

    public CardBalanceDTO getBalanceByNumber(String number) {
        CustomUserDetails currentUser = profileService.getCurrentUser();

        Optional<CardEntity> optional;

        if (currentUser.getAuthorities().contains("PAYMENT")) {
            optional = cardRepository
                    .findCardBalanceByNumberAndVisibleTrue(number);
        } else {
            optional = cardRepository
                    .findCardBalanceByNumberAndCompanyIdAndVisibleTrue(
                            number,currentUser.getId());
        }

        if (optional.isEmpty()) {
            log.error("Card not found!");
            throw new ItemNotFoundException(
                    "Card not found!"
            );
        }

        CardEntity entity = optional.get();

        CardBalanceDTO dto = new CardBalanceDTO();
        dto.setBalance(entity.getBalance());

        return dto;
    }
}
