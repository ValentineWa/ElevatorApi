package com.elevator.elevatorApi.repositories;

import com.elevator.elevatorApi.database.Elevator;
import com.elevator.elevatorApi.database.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
