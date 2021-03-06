package th.ac.ku.atm.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {
    private RestTemplate restTemplate;

    public BankAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BankAccount> getCustomerBankAccount(int customerId) {
        String url = "http://localhost:8091/api/bankaccount/customer/" +
                customerId;
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }

    //Open account
    public void createBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount";

        restTemplate.postForObject(url, bankAccount, BankAccount.class);
    }
//    public void createBankAccount(BankAccount bankAccount) {
//        bankAccountList.add(bankAccount);
//    }

    //getBankAccounts
    public List<BankAccount> getBankAccountList() {
        String url = "http://localhost:8091/api/bankaccount/";

        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();
        return Arrays.asList(accounts);
    }
//    public List<BankAccount> getBankAccountList() {
//        return new ArrayList<>(this.bankAccountList);
//    }

    public BankAccount getBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;

        ResponseEntity<BankAccount> response =
                restTemplate.getForEntity(url, BankAccount.class);

        return response.getBody();
    }

    public void editBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId();
        restTemplate.put(url, bankAccount);
    }

    public void depositBankAccount(BankAccount newBankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                newBankAccount.getId();

        BankAccount account = getBankAccount(newBankAccount.getId());
        double totalBalance = account.getBalance() + newBankAccount.getBalance();
        newBankAccount.setBalance(totalBalance);

        restTemplate.put(url, newBankAccount);
    }

    public void withdrawBankAccount(BankAccount newBankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                newBankAccount.getId();

        BankAccount account = getBankAccount(newBankAccount.getId());

        if(account.getBalance() > newBankAccount.getBalance()) {
            double totalBalance = account.getBalance() - newBankAccount.getBalance();
            newBankAccount.setBalance(totalBalance);
            restTemplate.put(url, newBankAccount);
        }


    }


    public void deleteBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId();
        restTemplate.delete(url,bankAccount);
    }



}
