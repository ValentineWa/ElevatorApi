# ElevatorApi

### An Elevator API/Application.
Endpoints will :
1. Call the elevator from any floor to any other floor.
2. Get real time information about the elevator place, state, direction
   Additionally, get log information about events and save them into the database.
   Also save every SQL query which gets executed into the database with tracking
   information on who/where/what made the call.
   Requirements
   ● The building can have a configurable amount of floors and elevator moves 1 floor
   per 5 seconds
   ● Doors open/close over 2 seconds
   ● Unit testing is also required.

3.  Elevators must be able to move async - i.e. If I have 5 elevators all of which are moving
   in separation directions, I should get records logged about every single action by each
   elevator.
    These logs must be segregated based on place/state/direction/etc, plus a way to see all
   of them in real time.