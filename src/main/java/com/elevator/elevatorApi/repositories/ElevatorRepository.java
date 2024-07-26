package com.elevator.elevatorApi.repositories;

import com.elevator.elevatorApi.database.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {
}
