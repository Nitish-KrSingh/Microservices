package com.bootmicro.cards.service;

import javax.smartcardio.Card;

public interface ICardServices {
    /**
     *
     * @param mobileNumber Accepting mobile number to create card     *  Created or not
     */
    void createCard(String mobileNumber);
}
