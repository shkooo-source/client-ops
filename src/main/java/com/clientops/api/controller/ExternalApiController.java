package com.clientops.api.controller;

import com.clientops.domain.entity.Client;
import com.clientops.exception.InvalidApiKeyException;
import com.clientops.repository.ClientRepository;
import com.clientops.service.ApiRequestLogService;
import com.clientops.service.ClientService;
import com.clientops.service.IncidentService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ExternalApiController {

    private final ClientService clientService;
    private final ApiRequestLogService apiRequestLogService;
    private final IncidentService incidentService;
    private final ClientRepository clientRepository;


    public ExternalApiController(
        ClientService clientService,
        ApiRequestLogService apiRequestLogService,
        IncidentService incidentService,
        ClientRepository clientRepository
    ) {
        this.clientService = clientService;
        this.apiRequestLogService = apiRequestLogService;
        this.incidentService = incidentService;
        this.clientRepository = clientRepository;
    }

    /**
     * 외부 고객사 API 예시
     */
    @GetMapping("/orders")
    public ResponseEntity<String> getOrders(@RequestHeader("X-API-KEY") String apiKey) {

        try {

            Client client = clientService.validateApiKey(apiKey);

            // 🔥 API 요청 로그 저장
            apiRequestLogService.saveRequestLog(
                client,
                apiKey,            // 🔥 apiKey 추가
                "/api/v1/orders",
                "GET",
                200
            );

            return ResponseEntity.ok("Orders for client: " + client.getName());

        } catch (InvalidApiKeyException e) {

        // 1️⃣ 인증 실패 로그
        apiRequestLogService.saveRequestLog(
                null,
                apiKey,    // 🔥 api_key 저장
                "/api/v1/orders",
                "GET",
                401
        );

        // 2️⃣ client가 특정되는 경우만 Incident 판단
        incidentService.checkAndCreateAuthFailureIncidentByApiKey(apiKey);


        // 3️⃣ 401 응답
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid API Key");
        }

    }

}