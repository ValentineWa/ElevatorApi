package com.elevator.elevatorApi.services;

import com.elevator.elevatorApi.database.ElevatorLog;
import com.elevator.elevatorApi.repositories.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public ElevatorLog logEvent(ElevatorLog elevatorLog){
        log.info("The logging deets are as follows: "+log);
        return logRepository.save(elevatorLog);
    }
}
