package dev.iseif.reactiverestapi.service;

import dev.iseif.reactiverestapi.model.Customer;
import dev.iseif.reactiverestapi.repository.CustomerRepositor;
import dev.iseif.reactiverestapi.repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class CustomerService {
	
  @Autowired 
  private CustomerRepositor customerRepositor;
 

 
  public CustomerService(CustomerRepositor customerRepositor) {
    this.customerRepositor = customerRepositor;
  }

  public Mono<List<Customer>> getAll() {
	  
	  //return Mono.defer(() -> {
		  return customerRepositor.findAll();
	  //}).subscribeOn(Schedulers.elastic());
	  
    //return customerRepositor.findAll();
  }

  public Mono<Customer> getById(String id) {
    return customerRepositor.findById(id);
  }

  

  public Mono<Customer> create(Customer customer) {
    return customerRepositor.save(customer);
  }

  public Mono<Customer> update(String id, Customer updateCustomer) {
    return customerRepositor.findById(id)
        .map(existingCustomer -> existingCustomer.toBuilder()
              .customerName(updateCustomer.getCustomerName())
              .address(updateCustomer.getAddress())
              .productIdList(updateCustomer.getProductIdList())
              .build())
        .flatMap(customerRepositor::save);
  }

  public Mono<Customer> deleteById(String id) {
    return customerRepositor.findById(id)
        .flatMap(Customer -> customerRepositor.delete(Customer).then(Mono.just(Customer)));
  }
}
