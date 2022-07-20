package com.company.service;

import com.company.config.CustomUserDetails;
import com.company.dto.ClientCreateOrUpdateDTO;
import com.company.dto.ClientDTO;
import com.company.dto.ClientFilterDTO;
import com.company.entity.ClientEntity;
import com.company.enums.ProfileRole;
import com.company.exceptions.ProfileAlreadyExists;
import com.company.exceptions.ProfileNotFoundException;
import com.company.repository.ClientRepository;
import com.company.repository.custom.CustomClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;

    private final ProfileService profileService;

    private final CustomClientRepository customClientRepository;

    public ClientService(ClientRepository clientRepository, ProfileService profileService, CustomClientRepository customClientRepository) {
        this.clientRepository = clientRepository;
        this.profileService = profileService;
        this.customClientRepository = customClientRepository;
    }

    public void create(ClientCreateOrUpdateDTO dto) {
        if (clientRepository.existsByPassportSeriesAndPassportNumberAndVisibleTrue(dto.getPassportSeries(),dto.getPassportNumber())) {
            log.error("Client already exists");
            throw new ProfileAlreadyExists(
                    "Client already exists"
            );
        }

        ClientEntity entity = toEntity(dto);

        clientRepository.save(entity);
    }


    public void update(String id, ClientCreateOrUpdateDTO dto) {
        Optional<ClientEntity> optional =
                clientRepository.findByIdAndVisibleTrue(id);

        if (optional.isEmpty()) {
            log.error("Client not found");
            throw new ProfileNotFoundException(
                    "Client not found"
            );
        }

        ClientEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setMiddleName(dto.getMiddleName());
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setPassportSeries(dto.getPassportSeries());

        clientRepository.save(entity);
    }

    public List<ClientDTO> paginationWithFilter(int size, int page, ClientFilterDTO dto) {
        List<ClientEntity> list = customClientRepository.paginationWithFilter(size, page, dto);

        return toResponseList(list);
    }

    private List<ClientDTO> toResponseList(List<ClientEntity> list) {
        List<ClientDTO> dtoList = new LinkedList<>();

        list.forEach(entity -> {
            ClientDTO dto = new ClientDTO();
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setMiddleName(entity.getMiddleName());
            dto.setPhone(entity.getPhone());
            dto.setPassportSeries(entity.getPassportSeries());
            dto.setPassportNumber(entity.getPassportNumber());
            dto.setCreatedDate(entity.getCreatedDate());
            dto.setId(entity.getId());

            dtoList.add(dto);
        });

        return dtoList;
    }

    private ClientEntity toEntity(ClientCreateOrUpdateDTO dto) {
        ClientEntity entity = new ClientEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setMiddleName(dto.getMiddleName());
        entity.setPassportNumber(dto.getPassportNumber());
        entity.setPassportSeries(dto.getPassportSeries());
        entity.setPhone(dto.getPhone());

        return entity;
    }

    public ClientDTO getById(String id) {
        CustomUserDetails currentUser = profileService.getCurrentUser();

        Optional<ClientEntity> optional;

        if (currentUser.getAuthorities().contains(ProfileRole.ROLE_ADMIN)) {
            optional = clientRepository.findByIdAndVisibleTrue(id);
        } else {
            optional = clientRepository
                    .findByIdAndCompanyIdAndVisibleTrue(id,currentUser.getId());
        }

        if (optional.isEmpty()) {
            log.error("Client not found");
            throw new ProfileNotFoundException(
                    "Client not found"
            );
        }

        ClientEntity entity = optional.get();

        return toDTO(entity);
    }

    private ClientDTO toDTO(ClientEntity entity) {
        ClientDTO dto = new ClientDTO();
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setMiddleName(entity.getMiddleName());
        dto.setPhone(entity.getPhone());
        dto.setPassportSeries(entity.getPassportSeries());
        dto.setPassportNumber(entity.getPassportNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setId(entity.getId());

        return dto;
    }

    public boolean exists(String clientId) {
        return clientRepository.existsByIdAndVisibleTrue(clientId);
    }
}
