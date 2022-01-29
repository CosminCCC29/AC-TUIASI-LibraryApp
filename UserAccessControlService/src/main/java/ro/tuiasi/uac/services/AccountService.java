package ro.tuiasi.uac.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.tuiasi.uac.interfaces.AccountServiceInterface;
import ro.tuiasi.uac.interfaces.HashingServiceInterface;
import ro.tuiasi.uac.models.Account;
import ro.tuiasi.uac.repositories.AccountRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService implements AccountServiceInterface, UserDetailsService {

    @Autowired
    HashingServiceInterface hashingService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByCredentials(String email, String password) {
        return this.accountRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Account addAccount(Account account) {
        account.setPassword(hashingService.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccountById(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {

        int accountIdentifier = -1;
        try {
            accountIdentifier = Integer.parseInt(accountId);
        }
        catch (Exception exception)
        {
            throw new UsernameNotFoundException("Invalid account id.");
        }

        Optional<Account> accountOptional = accountRepository.findById(accountIdentifier);
        if(accountOptional.isEmpty())
            throw new UsernameNotFoundException("Username does not exists.");

        Account account = accountOptional.get();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));

        return new User(account.getEmail(), account.getPassword(), authorities);
    }
}
