/*************************************************************************************************
 *
 * RuTubeAccountService.java - The RuTubeAccountService class.
 *
 * Copyright (c) 2016 Sverrir Magnússon & Kári Eiríksson. 
 * All rights reserved.
 *
 **************************************************************************************************/

package is.ru.honn.rutube.services;

import is.ru.honn.rutube.domain.Account;
import is.ru.honn.rutube.domain.AccountRegistration;
import is.ru.honn.rutube.domain.Token;

/**
 * RuTube account service.
 *
 * @author Sverrir
 * @version 1.0, 26 okt. 2016
 */
public class RuTubeAccountService implements AccountService {
    public RuTubeAccountService() {}

    @Override
    public boolean register(AccountRegistration accountRegistration) {
        return false;
    }

    @Override
    public boolean updateAccountData(AccountRegistration updatedAccountRegistration) {
        return false;
    }

    @Override
    public Token login(Account account) {
        return new Token("Sverrir");
    }

    @Override
    public boolean isValidAccountToken(Token token) {
        return false;
    }

    @Override
    public boolean deleteAccount(String username) {
        return false;
    }
}