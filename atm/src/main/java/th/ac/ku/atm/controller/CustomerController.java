package th.ac.ku.atm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atm.model.Customer;
import th.ac.ku.atm.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public String getCustomerPage(Model model) {
        model.addAttribute("title","All Customers");
        model.addAttribute("titleRegister","Register Customer");
        model.addAttribute("allCustomers",customerService.getCustomerList());
        return "customer";
    }

    @PostMapping()
    public String registerAccount(@ModelAttribute Customer customer, Model model) {
        customerService.createCustomer(customer);
        model.addAttribute("allCustomer",customerService.getCustomerList());
        return "redirect:customer";
    }
}
