package vn.elca.service;

import lombok.RequiredArgsConstructor;
import vn.elca.exception.CustomerNotFoundException;
import vn.elca.model.Customer;
import vn.elca.repository.CustomerRepository;
import javax.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findOne(long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException());
    }

}
