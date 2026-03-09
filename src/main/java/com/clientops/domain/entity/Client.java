package com.clientops.domain.entity;

import jakarta.persistence.*;

@Entity
// 이 클래스는 DB 테이블 대상
@Table(name = "clients")
public class Client {
    
    @Id
    // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 64)
    private String apiKey;

    @Column(nullable = false)
    private boolean active = true;

    protected Client() {
        // JPA 기본 생성자 ( * JPA 규칙 )
    }

    public Client(String name, String apiKey) {
        this.name = name;
        this.apiKey = apiKey;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

}