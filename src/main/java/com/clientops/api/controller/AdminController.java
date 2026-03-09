package com.clientops.api.controller;

import com.clientops.api.dto.ApiRequestLogResponse;
import com.clientops.api.dto.DashboardResponse;
import com.clientops.repository.ApiRequestLogRepository;
import com.clientops.service.ApiRequestLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/api/logs")
public class AdminController {
    private final ApiRequestLogService apiRequestLogService;
    private final ApiRequestLogRepository apiRequestLogRepository;

    public AdminController(ApiRequestLogService apiRequestLogService, ApiRequestLogRepository apiRequestLogRepository) {
        this.apiRequestLogService = apiRequestLogService;
        this.apiRequestLogRepository = apiRequestLogRepository;
    }

    /**
     * 전체 API 요청 로그 조회 (관리자용)
     * 예) /internal/api/logs?page=0&size=20
    */
    @GetMapping
    public ResponseEntity<Page<ApiRequestLogResponse>> getApiRequestLogs(
        @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(apiRequestLogService.getLogs(pageable));
    }

    // 대시보드용 메서드
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboardStats() {
        // 1. 전체 로그 수 조회 (apiRequestLogRepository.count() 사용)
        long total = apiRequestLogRepository.count();

        // 2. 에러 로그 수 조회 (401 등)
        // 리포지토리에 "countByStatusCodeGreaterThanEqual(400)" 같은 메서드 추가 필요
        long errors = apiRequestLogRepository.countByStatusCodeGreaterThanEqual(400);

        // 3. 상위 고객사는 일단 빈 리스트나 간단한 로직으로 대체
        return ResponseEntity.ok(new DashboardResponse(total, errors, List.of()));
    }
}
