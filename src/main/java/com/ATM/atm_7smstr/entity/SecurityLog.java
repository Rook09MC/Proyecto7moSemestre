package com.ATM.atm_7smstr.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "security_logs")
public class SecurityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long log_id;

    private String event_type;

    private String description;

    private LocalDateTime created_at;

    private Long user_id;

    public SecurityLog() {}

    public SecurityLog(String event_type, String description) {
        this.event_type = event_type;
        this.description = description;
        this.created_at = LocalDateTime.now();
    }

    public Long getLog_id() {
        return log_id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}