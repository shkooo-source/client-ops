package com.clientops.service;

import com.clientops.domain.entity.Client;
import com.clientops.exception.InvalidApiKeyException;
import com.clientops.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * API Key로 Client를 조회하고 유효성 검증
     */
    public Client validateApiKey(String apiKey) {
        Client client = clientRepository.findByApiKey(apiKey).orElseThrow(() -> new InvalidApiKeyException("Invalid API Key"));
    
        if (!client.isActive()) {
            throw new InvalidApiKeyException("Inactive client");
        }

        return client;
    }

}