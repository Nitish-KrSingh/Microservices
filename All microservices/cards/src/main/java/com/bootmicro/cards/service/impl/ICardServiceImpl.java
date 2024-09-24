package com.bootmicro.cards.service.impl;

import com.bootmicro.cards.constants.CardsConstants;
import com.bootmicro.cards.dto.CardsDto;
import com.bootmicro.cards.entity.Cards;
import com.bootmicro.cards.exception.CardAlreadyExistException;
import com.bootmicro.cards.exception.ResourceNotFoundException;
import com.bootmicro.cards.mapper.CardMapper;
import com.bootmicro.cards.repository.CardsRepository;
import com.bootmicro.cards.service.ICardServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public  class ICardServiceImpl implements ICardServices {


    public CardsRepository cardsRepository;


    /**
     * @param mobileNumber Accepting mobile number to create card
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);
        if (cards.isPresent()) {
            throw new CardAlreadyExistException("Card is already exist in the system");
        }
        Cards newCard = createNewCard(mobileNumber);
        cardsRepository.save(newCard);

    }


    private Cards createNewCard(String mobileNumber) {
        Cards card = new Cards();
        card.setMobileNumber(mobileNumber);
        long newCardNumber = 1000000000L + new Random().nextInt(900000000);
        card.setCardNumber(String.valueOf(newCardNumber));
        card.setCardType(CardsConstants.CREDIT_CARD);
        card.setTotalLimit((long) CardsConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0L);
        card.setAvailableAmount(1000L);

        return card;
    }

    /*
     * @param mobileNumber accepting mobile number
     * @return card details
     */
    @Override
    public CardsDto fetchCards(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->  new ResourceNotFoundException("Card not found")
        );
        return CardMapper.cardToCardDto( cards , new CardsDto());
    }

    /**
     * @param cardDto accepting card details
     * @return updated or not
     */
    @Override
    public boolean updateCard(CardsDto cardDto) {
    boolean isUpdated = false;
    if (cardDto != null) {

        Cards cards = cardsRepository.findByMobileNumber(cardDto.getMobileNumber()).orElseThrow(
                ()->  new ResourceNotFoundException("Card not found")
        );
        CardMapper.cardDtoTOCard(cardDto, cards);
        cardsRepository.save(cards);
        isUpdated = true;
    }
        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->  new ResourceNotFoundException("Card not found")
        );

        cardsRepository.deleteByMobileNumber(mobileNumber);
        return true;

    }

}
