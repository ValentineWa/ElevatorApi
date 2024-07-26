package com.elevator.elevatorApi.database;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "tbl_log_info")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp timestamp;
    private Long elevatorId;
    private String event;
    private String user;
    private String query;
}
