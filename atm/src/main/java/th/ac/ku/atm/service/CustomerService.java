package th.ac.ku.atm.service;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import th.ac.ku.atm.model.Customer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private List<Customer> customerList;

    @PostConstruct
    public void postConstruct() {
        this.customerList = new ArrayList<>();
    }

    public void createCustomer(Customer customer) {
        String hashPin = hash(customer.getPin());
        customer.setPin(hashPin);
        customerList.add(customer);
    }

    public String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin,salt);
    }

    public List<Customer> getCustomerList() {
        return new ArrayList<>(this.customerList);
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
        for(Customer customer : customerList) {
            if(customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }
}
