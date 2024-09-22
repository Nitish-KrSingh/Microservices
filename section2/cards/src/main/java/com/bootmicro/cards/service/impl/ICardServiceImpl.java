package com.bootmicro.cards.service.impl;

import com.bootmicro.cards.constants.CardsConstants;
import com.bootmicro.cards.entity.Cards;
import com.bootmicro.cards.exception.CardAlreadyExistException;
import com.bootmicro.cards.repository.CardsRepository;
import com.bootmicro.cards.service.ICardServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class ICardServiceImpl implements ICardServices {


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
}
