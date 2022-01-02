package ro.tuiasi.uac.services.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.account.AccountServiceInterface;
import ro.tuiasi.uac.models.Account;
import ro.tuiasi.uac.repositories.AccountRepository;

import java.util.Optional;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountById(int id) {
        accountRepository.deleteById(id);
    }
}
