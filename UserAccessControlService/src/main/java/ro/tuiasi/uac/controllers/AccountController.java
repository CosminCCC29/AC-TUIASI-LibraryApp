package ro.tuiasi.uac.controllers;

import io.spring.guides.gs_producing_web_service.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ro.tuiasi.uac.interfaces.AccountServiceInterface;
import ro.tuiasi.uac.interfaces.HashingServiceInterface;
import ro.tuiasi.uac.models.Account;

import java.util.Optional;

@Endpoint
public class AccountController {

    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    AccountServiceInterface accountService;

    @Autowired
    HashingServiceInterface hashingService;

    @SneakyThrows
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAccountByIdRequest")
    @ResponsePayload
    public GetAccountResponse getAccountById(@RequestPayload GetAccountByIdRequest accountByIdRequest) {
        Optional<Account> optionalAccount = accountService.getAccountById(accountByIdRequest.getId());

        if(optionalAccount.isPresent())
        {
            Account account = optionalAccount.get();
            GetAccountResponse accountResponse = new GetAccountResponse();
            AccountData accountData = new AccountData();
            accountData.setId(account.getId());
            accountData.setEmail(account.getEmail());
            accountData.setPassword(account.getPassword());
            accountResponse.setAccountData(accountData);
            return accountResponse;
        }

        throw new Exception("The account does not exists!");
    }

    @SneakyThrows
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddAccountRequest")
    @ResponsePayload
    public AddAccountResponse postAccount(@RequestPayload AddAccountRequest addAccountRequest) {
        AccountData accountData = addAccountRequest.getAccountData();
        Account account = new Account();

        account.setId(accountData.getId());
        account.setEmail(accountData.getEmail());
        account.setPassword(accountData.getPassword());
        account.setRole(accountData.getRole());

        Account addedAccount = accountService.addAccount(account);

        AddAccountResponse addAccountResponse = new AddAccountResponse();
        AccountData newAccountData = new AccountData();
        newAccountData.setId(addedAccount.getId());
        newAccountData.setEmail(addedAccount.getEmail());
        newAccountData.setPassword(addedAccount.getPassword());
        newAccountData.setRole(addedAccount.getRole());

        addAccountResponse.setAccountData(newAccountData);
        return addAccountResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteAccountByIdRequest")
    @ResponsePayload
    public DeleteAccountByIdResponse deleteAccountById(@RequestPayload DeleteAccountByIdRequest deleteAccountRequest)
    {
        accountService.deleteAccountById(deleteAccountRequest.getId());

        DeleteAccountByIdResponse deleteAccountByIdResponse = new DeleteAccountByIdResponse();
        deleteAccountByIdResponse.setId(deleteAccountRequest.getId());
        return deleteAccountByIdResponse;
    }
}

