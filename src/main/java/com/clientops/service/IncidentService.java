package com.clientops.service;

import com.clientops.domain.entity.Incident;
import com.clientops.api.dto.IncidentResponse;
import com.clientops.repository.IncidentRepository;
import com.clientops.repository.ApiRequestLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class IncidentService {

    // 필드
    private final IncidentRepository incidentRepository;
    private final ApiRequestLogRepository apiRequestLogRepository;

    // 생성자
    public IncidentService(
            IncidentRepository incidentRepository,
            ApiRequestLogRepository apiRequestLogRepository
    ) {
        this.incidentRepository = incidentRepository;
        this.apiRequestLogRepository = apiRequestLogRepository;
    }

    /**
     * apiKey 기준 인증 실패 누적 → Incident 생성
     * 조건: 5분 내 3회 이상 실패
     */
    public void checkAndCreateAuthFailureIncidentByApiKey(String apiKey) {

        // 1️⃣ 최근 5분 기준
        LocalDateTime from = LocalDateTime.now().minusMinutes(5);

        // 2️⃣ 실패 횟수 조회
        long failCount =
                apiRequestLogRepository.countRecentFailuresByApiKey(
                        apiKey,
                        from
                );

        if (failCount < 3) {
            return;
        }

        // 3️⃣ 중복 Incident 방지
        boolean exists =
                incidentRepository.existsByTitleAndStatus(
                        "AUTH_FAIL:" + apiKey,
                        "OPEN"
                );

        if (exists) {
            return;
        }

        // 4️⃣ Incident 생성
        Incident incident = new Incident(
                null,                       // client 없음
                "AUTH_FAIL:" + apiKey,
                "OPEN"
        );

        incidentRepository.save(incident);
    }

    /*
    * Incident 해제
    */
    @Transactional
    public void resolveIncident(Long incidentId) {
        Incident incident = incidentRepository.findByIdAndStatus(
                incidentId, "OPEN").orElseThrow(() -> 
                new IllegalStateException("OPEN 상태의 Incident가 없습니다.")
        );
        incident.resolve();
    }

    /*
    * Incident 조회
    */
    @Transactional(readOnly = true)
    public List<IncidentResponse> getIncidents(String status) {
        List<Incident> incidents;

        if (status == null) {
            incidents = incidentRepository.findAllByOrderByCreatedAtDesc();
        } else {
            incidents = incidentRepository.findAllByStatusOrderByCreatedAtDesc(status);
        }

        return incidents.stream().map(IncidentResponse::from).toList();
    }

}
