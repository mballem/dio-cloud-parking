package me.dio.parking.controller.mapper;

import me.dio.parking.dto.ParkingDTO;
import me.dio.parking.model.Parking;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDTO parkingDTO(Parking parking) {
        return MODEL_MAPPER.map(parking, ParkingDTO.class);
    }

    public List<ParkingDTO> toParkingDTOList(List<Parking> parkings) {
        return parkings.stream().map(this::parkingDTO).collect(Collectors.toList());
    }
}
