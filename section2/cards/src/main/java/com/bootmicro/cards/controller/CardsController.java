package com.bootmicro.cards.controller;

import com.bootmicro.cards.constants.CardsConstants;
import com.bootmicro.cards.dto.CardsDto;
import com.bootmicro.cards.dto.ResponseDto;
import com.bootmicro.cards.entity.Cards;
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
    public ResponseEntity<CardsDto> fetchCard(@RequestParam String mobileNumber){
        CardsDto cardsDto = cardServices.fetchCards(mobileNumber);
        return ResponseEntity.status(HttpStatus.FOUND).body(cardsDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseDto> updateCard(@RequestBody CardsDto cardsDto){
        if(cardServices.updateCard(cardsDto)){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam String mobileNumber){
        if(cardServices.deleteCard(mobileNumber)){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(CardsConstants.STATUS_500, CardsConstants.MESSAGE_500));
        }
    }
}
