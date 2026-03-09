package com.clientops.api.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class DashboardResponse {
    private long totalRequests;
    private long errorRequests;
    private double errorRate;
    private List<TopClientDto> topClients;

    public DashboardResponse(long totalRequests, long errorRequests, List<TopClientDto> topClients) {
        this.totalRequests = totalRequests;
        this.errorRequests = errorRequests;
        this.errorRate = totalRequests == 0 ? 0 : (double) errorRequests / totalRequests * 100;
        this.topClients = topClients;
    }
}
