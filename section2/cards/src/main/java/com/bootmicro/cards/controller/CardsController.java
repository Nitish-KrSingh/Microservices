package com.bootmicro.cards.controller;

import com.bootmicro.cards.constants.CardsConstants;
import com.bootmicro.cards.dto.CardsDto;
import com.bootmicro.cards.dto.ResponseDto;
import com.bootmicro.cards.service.ICardServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(value = "/api" , produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CardsController {

    public ICardServices cardServices;

    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam String mobileNumber){
        cardServices.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }


    @GetMapping(value = "/fetch")
    public ResponseEntity<ResponseDto> fetchCard(@RequestParam String mobileNumber){

        return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardsDto cardsDto){

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
    }
}
