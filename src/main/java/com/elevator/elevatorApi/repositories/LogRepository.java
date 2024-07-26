package com.elevator.elevatorApi.repositories;

import com.elevator.elevatorApi.database.ElevatorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<ElevatorLog, Long> {
}
