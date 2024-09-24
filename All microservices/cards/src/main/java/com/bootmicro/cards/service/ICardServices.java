package com.bootmicro.cards.service;

import com.bootmicro.cards.dto.CardsDto;

public interface ICardServices {
    /**
     *
     * @param mobileNumber Accepting mobile number to create card     *  Created or not
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber accepting mobile number
     * @return  card details
     */
    CardsDto fetchCards(String mobileNumber);

    /**
     *
     * @param card accepting card details
     * @return  updated or not
     */
    boolean updateCard(CardsDto card);

    /**
     *
     * @param mobileNumber
     * @return
     */
    boolean deleteCard(String mobileNumber);
}
