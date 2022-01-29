package ro.tuiasi.uac.interfaces;
import ro.tuiasi.uac.models.Account;

import java.util.Optional;

public interface AccountServiceInterface {

    Optional<Account> getAccountById(int id);
    Optional<Account> getAccountByCredentials(String email, String password);
    Account addAccount(Account account);
    void deleteAccountById(int id);
}
