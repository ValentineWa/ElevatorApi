package com.elevator.elevatorApi.services;

import com.elevator.elevatorApi.database.Elevator;
import com.elevator.elevatorApi.repositories.ElevatorRepository;
import com.elevator.elevatorApi.repositories.LogRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class ElevatorService {
    @Value("${elevator.count}")
    private int elevatorNumber;
    @Autowired
    private ElevatorRepository elevatorRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogService logService;


//TODO
//    //initializeElevators: Initializes a predefined number of elevators with default attributes and saves them to the database.
//    //createElevator: Create a new Elevator entity with specified attributes.
//    callElevator: Finds the nearest idle elevator and initiates the movement.
//    moveElevator: Handles the actual movement and state changes of the elevator.
//    getLogs: Retrieves all log entries.
//    logEvent: Logs an event with details about the elevator, event, user, and query.


    //Use this to get the elevator's data when starting application.
    @PostConstruct
    public void initializeElevators(){
        List<Elevator> elevators =  new ArrayList<>();

        for(int i = 0; i < elevatorNumber; i++){
            Elevator elevator = new Elevator();
            elevator.setCurrentFloor(0);
            elevator.setDirection("idle");
            elevator.setState("idle");
            elevator.setDoorState("closed");
            elevators.add(elevator);
        }
        log.info("The elevator deets are:[]" + elevators);
        elevatorRepository.saveAll(elevators);
    }


    //Create an Elevator
    public Elevator createElevator(Elevator elevator) {
        log.info("Evelator details have been saved: []" + elevator);
        return elevatorRepository.save(elevator);
    }


    //    callElevator: Finds the nearest idle elevator and initiates the movement.
    public void callElevator(int fromFloor, int toFloor, Long elevatorId, String user) throws InterruptedException {
        // Implement logic to move the elevator asynchronously

        //Find the nearest elevator
        List<Elevator> elevators = elevatorRepository.findAll();
        Optional<Elevator> idleElevator = elevators.stream()
                .filter(elev -> "idle".equals(elev.getState()))
                .findFirst();
        if (idleElevator.isPresent()){
            Elevator elevator = idleElevator.get();
//            logService(elevator.getId(), "Called from floor "+ fromFloor + "to floor " + toFloor);
            log.info( "Called from floor "+ fromFloor + "to floor " + toFloor);
            moveElevator(elevator, fromFloor, toFloor, user, elevatorId);
        }
    }

    public List<Elevator> getAllElevators() {
        return elevatorRepository.findAll();
    }

    public Elevator getElevatorStatus(Long elevatorId) {
        return elevatorRepository.findById(elevatorId).orElse(null);
    }

    private void moveElevator(Elevator elevator, int fromFloor, int toFloor, Long elevatorId, String user) throws InterruptedException {
        elevator.setState("moving");
        elevator.setDirection(fromFloor < toFloor ? "up" : "down");
        logService.logEvent(elevatorId, "Elevator moved from floor " + currentFloor + " to floor " + targetFloor, user, "UPDATE elevator SET current_floor = " + targetFloor + " WHERE id = " + elevatorId);
        elevatorRepository.save(elevator);

        //elevator movement
        int currentFloor = elevator.getCurrentFloor();
        while(currentFloor != fromFloor){
            Thread.sleep(5000);
            currentFloor += fromFloor < toFloor ? 1 : -1;
            elevator.setCurrentFloor(currentFloor);
            logService.logEvent(elevatorId, "Elevator moved from floor " + currentFloor + " to floor " + toFloor, user, "UPDATE elevator SET current_floor = " + toFloor + " WHERE id = " + elevatorId);
            elevatorRepository.save(elevator);
        }

        //door opening pickup
        elevator.setDoorState("open");
        log.info( "Doors opening at floor "+ fromFloor);
        //simulate 2 sec for doors to open
        Thread.sleep(2000);
        logService.logEvent(elevatorId, "Elevator doors opened", user, "UPDATE elevator SET door_state = 'open' WHERE id = " + elevatorId);


        //Move to destination floor
        elevator.setDoorState("closed");
        log.info( "Doors closing at floor "+ fromFloor);
        logService.logEvent(elevatorId, "Elevator doors closed", user, "UPDATE elevator SET door_state = 'closed' WHERE id = " + elevatorId);
        while(currentFloor != toFloor){
            Thread.sleep(5000);
            currentFloor += fromFloor < toFloor ? 1 : -1;
            elevator.setCurrentFloor(currentFloor);
            elevatorRepository.save(elevator);
        }

        //door opening at dropoff
        elevator.setDoorState("open");
        logService.logEvent(elevatorId, "Elevator doors opened", user, "UPDATE elevator SET door_state = 'open' WHERE id = " + elevatorId);
        log.info( "Doors opening at destination floor "+ fromFloor);
        Thread.sleep(2000);

        //Elevator in idle state
        elevator.setDoorState("closed");
        elevator.setState("idle");
        elevator.setDirection(null);
        elevatorRepository.save(elevator);
        logService.logEvent(elevatorId, "Elevator doors closed", user, "UPDATE elevator SET door_state = 'closed' WHERE id = " + elevatorId);
    }

}
