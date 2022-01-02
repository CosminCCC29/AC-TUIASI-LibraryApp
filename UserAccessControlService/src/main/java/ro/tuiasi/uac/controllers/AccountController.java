package ro.tuiasi.uac.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuiasi.uac.interfaces.account.AccountServiceInterface;
import ro.tuiasi.uac.models.Account;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/userinformation/accounts")
public class AccountController {

    @Autowired
    AccountServiceInterface accountService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable int id) {;
        return ResponseEntity.status(200).body(accountService.getAccountById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> postAccount(@RequestBody Account account) {
        accountService.addAccount(account);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable int id)
    {
        accountService.deleteAccountById(id);
        return ResponseEntity.status(200).build();
    }

}
