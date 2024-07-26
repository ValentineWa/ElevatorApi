package com.elevator.elevatorApi.controllers;

import com.elevator.elevatorApi.database.Elevator;
import com.elevator.elevatorApi.database.Log;
import com.elevator.elevatorApi.services.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ElevatorContoller {

    @Autowired
    private ElevatorService elevatorService;

    @PostMapping("/createElevator")
    public ResponseEntity<Elevator> createElevator(@RequestBody Elevator elevator) {
        Elevator createElevator = elevatorService.createElevator(elevator);
        return ResponseEntity.ok(createElevator);
    }
    @PostMapping("/call")
    public ResponseEntity<Void> callElevator(@RequestParam int fromFloor, @RequestParam int toFloor) throws InterruptedException {
        elevatorService.callElevator(fromFloor, toFloor);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<Elevator>> getAllElevators() {
        return ResponseEntity.ok(elevatorService.getAllElevators());
    }
//
//    @GetMapping("/status")
//    public ResponseEntity<Elevator> getElevatorStatus(@RequestParam Long elevatorId) {
//        return ResponseEntity.ok(elevatorService.getElevatorStatus(elevatorId));
//    }
//
//    @GetMapping("/logs")
//    public ResponseEntity<List<Log>> getLogs() {
//        return ResponseEntity.ok(elevatorService.getLogs());
//    }

}
