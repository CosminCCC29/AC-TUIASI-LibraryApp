package ro.tuiasi.uac.interfaces.account;
import ro.tuiasi.uac.models.Account;

import java.util.Optional;

public interface AccountServiceInterface {

    Optional<Account> getAccountById(int id);
    Account addAccount(Account account);
    void deleteAccountById(int id);
}
