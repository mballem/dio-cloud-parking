package me.dio.parking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.parking.controller.mapper.ParkingMapper;
import me.dio.parking.dto.ParkingCreateDTO;
import me.dio.parking.dto.ParkingDTO;
import me.dio.parking.model.Parking;
import me.dio.parking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "parkingapi")
@Tag(name = "Parking Controller", description = "Recursos do tipo Parking")
@RestController
@RequestMapping("parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @Operation(summary = "Find All parkings", description = "List of parkings")
    @GetMapping
    public ResponseEntity<List<ParkingDTO>> findAll() {
        List<Parking> parkings = parkingService.findAll();
        List<ParkingDTO> dtoList = parkingMapper.toParkingDTOList(parkings);
        return ResponseEntity.ok(dtoList);
    }

    @Operation(summary = "Find parking by Id", description = "Return parking by id")
    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
        Parking parking = parkingService.findById(id);
        ParkingDTO dto = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Create a parking")
    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO createDto) {
        Parking parking = parkingMapper.toParking(createDto);
        Parking newParking = parkingService.create(parking);
        ParkingDTO parkingDto = parkingMapper.toParkingDTO(newParking);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(parkingDto);
    }

    @Operation(summary = "Update a parking by id")
    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@RequestBody ParkingCreateDTO dto, @PathVariable String id) {
        Parking parking = parkingMapper.toParking(dto);
        Parking newParking = parkingService.update(id, parking);
        ParkingDTO parkingDto = parkingMapper.toParkingDTO(newParking);
        return ResponseEntity.ok(parkingDto);
    }

    @Operation(summary = "Delete parking by Id", description = "Remove parking by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a parking by id ans insert a check-out date")
    @PatchMapping("/{id}")
    public ResponseEntity<ParkingDTO> exitDate(@PathVariable String id,
                                               @RequestParam("day") Integer day,
                                               @RequestParam("month") Integer month,
                                               @RequestParam("year") Integer year,
                                               @RequestParam("hour") Integer hour,
                                               @RequestParam("minute") Integer minute) {

        Parking newParking = parkingService.checkOut(id, day, month, year, hour, minute);
        ParkingDTO parkingDto = parkingMapper.toParkingDTO(newParking);
        return ResponseEntity.ok(parkingDto);
    }
}
