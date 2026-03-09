package com.clientops.service;

import com.clientops.api.dto.ApiRequestLogResponse;
import com.clientops.domain.entity.ApiRequestLog;
import com.clientops.domain.entity.Client;
import com.clientops.repository.ApiRequestLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApiRequestLogService {
    private final ApiRequestLogRepository apiRequestLogRepository;

    public ApiRequestLogService(ApiRequestLogRepository apiRequestLogRepository) {
        this.apiRequestLogRepository = apiRequestLogRepository;
    }

    // [추가] 로그 목록 조회 (페이징 처리)
    public Page<ApiRequestLogResponse> getLogs(Pageable pageable) {
        return apiRequestLogRepository.findAllByOrderByRequestTimeDesc(pageable).map(ApiRequestLogResponse::new);
    }

    public void saveRequestLog(
        Client client,
        String apiKey,
        String endpoint,
        String httpMethod,
        int statusCode
    ) {
        ApiRequestLog log = new ApiRequestLog(
                endpoint,      // String
                httpMethod,    // String
                statusCode,    // int
                apiKey,        // String
                client         // Client
        );

        apiRequestLogRepository.save(log);
    }

}