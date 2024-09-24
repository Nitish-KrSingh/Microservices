package com.bootmicro.cards.mapper;

import com.bootmicro.cards.dto.CardsDto;
import com.bootmicro.cards.entity.Cards;


public class CardMapper {

    public static Cards cardDtoTOCard(CardsDto cardsDto, Cards card) {
        card.setAvailableAmount(cardsDto.getAvailableAmount());
        card.setCardNumber(cardsDto.getCardNumber());
        card.setCardType(cardsDto.getCardType());
        card.setAmountUsed(cardsDto.getAmountUsed());
        card.setTotalLimit(cardsDto.getTotalLimit());
        card.setMobileNumber(cardsDto.getMobileNumber());
        return card;
    }

    public static CardsDto cardToCardDto(Cards card , CardsDto cardDto) {

        cardDto.setCardType(card.getCardType());
        cardDto.setMobileNumber(card.getMobileNumber());
        cardDto.setAmountUsed(card.getAmountUsed());
        cardDto.setCardNumber(card.getCardNumber());
        cardDto.setAvailableAmount(card.getAvailableAmount());
        cardDto.setTotalLimit(card.getTotalLimit());
        return cardDto;
    }
}
