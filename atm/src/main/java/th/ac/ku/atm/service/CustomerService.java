package th.ac.ku.atm.service;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.data.CustomerRepository;
import th.ac.ku.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void createCustomer(Customer customer) {
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        repository.save(customer);
    }

    public String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin,salt);
    }

    public List<Customer> getCustomerList() {
        return repository.findAll();
    }

    public Customer checkPin(Customer inputCustomer) {
        Customer storedCustomer = findCustomer(inputCustomer.getId());
        if(storedCustomer != null) {
            String hashPin = storedCustomer.getPin();
            if(BCrypt.checkpw(inputCustomer.getPin(),hashPin)) {
                return storedCustomer;
            }
        }
        return null;
    }

    public Customer findCustomer(int id) {
        try {
            return repository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
