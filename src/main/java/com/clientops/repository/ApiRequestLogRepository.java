package com.clientops.repository;

import com.clientops.domain.entity.ApiRequestLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ApiRequestLogRepository
        extends JpaRepository<ApiRequestLog, Long> {

    // [추가] 모든 로그를 최신순으로 페이징하여 조회
    Page<ApiRequestLog> findAllByOrderByRequestTimeDesc(Pageable pageable);

    @Query("""
        select count(l)
        from ApiRequestLog l
        where l.apiKey = :apiKey
          and l.statusCode = 401
          and l.requestTime >= :from
    """)
    long countRecentFailuresByApiKey(
            @Param("apiKey") String apiKey,
            @Param("from") LocalDateTime from
    );
    long countByStatusCodeGreaterThanEqual(int statusCode);
}
