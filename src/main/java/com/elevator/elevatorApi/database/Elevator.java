package com.elevator.elevatorApi.database;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "tbl_elevator")
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int currentFloor;
    private String direction;   //up | down
    private String state;   //idle | moving
    private String doorState;  //open | closed

}