package dev.iseif.reactiverestapi.controller;

import dev.iseif.reactiverestapi.model.Customer;
import dev.iseif.reactiverestapi.model.CustomerInfo;
import dev.iseif.reactiverestapi.model.CustomerProduct;
import dev.iseif.reactiverestapi.model.Product;
import dev.iseif.reactiverestapi.service.CustomerService;
import dev.iseif.reactiverestapi.service.ProductService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	
  private final CustomerService customerService;
  
  private final ProductService productService;

  public CustomerController(CustomerService customerService, ProductService productService) {
    this.customerService = customerService;
    this.productService = productService;
  }

  @GetMapping
  public Mono<Object> getAllCustomer() {
    //return customerService.getAll();
	  Mono<List<Customer>> customerAll = customerService.getAll();
	  Mono<List<Product>> productAll = productService.getAll();
	  
	  Mono<Tuple2<List<Customer>, List<Product>>> zip = Mono.zip(customerAll, productAll );
	  
	  Mono<Object> res = zip.map(t -> populate(t.getT1(),t.getT2()));
	  
	  return res;
  }

 




private CustomerInfo populate(List<Customer> customerList, List<Product> productList) {
	// TODO Auto-generated method stub
	
	Iterator<Customer> customerIter = customerList.iterator();
	List<CustomerProduct> customerProductList =new ArrayList<CustomerProduct>();
	  while (customerIter.hasNext()) {
		  Customer customer=customerIter.next();
		 
		  customerProductList.add( addCustomerProduct(customer, productList));
	  }
	  
	  CustomerInfo info = new CustomerInfo();
	  info.setCustomerProductList(customerProductList);
	
	return info;
}

private CustomerProduct addCustomerProduct(Customer customer, List<Product> productList) {
	// TODO Auto-generated method stub
	System.out.println("inside iterateCustomer customer="+customer);
	CustomerProduct customerProduct =new CustomerProduct();
	customerProduct.setCustomerId(customer.getCustomerId());
	customerProduct.setAddress(customer.getAddress());
	customerProduct.setCustomerName(customer.getCustomerName());
	List<Product> custProductList =new ArrayList<Product>();
	
	Iterator<String> iter = customer.getProductIdList().iterator();  
    while (iter.hasNext()) {
    	String productID=iter.next();
    	System.out.println("productID="+productID);
    	System.out.println("productList="+productList);
    	
    	{
    	  Iterator<Product> productIter = productList.iterator();
   		  while (productIter.hasNext()) {
   			Product product=productIter.next();
   			if(isProduct(productID, product)) {
   				custProductList.add(product);
   			}  
   		  }
   		
    	}
    	
    } 
    
    
    System.out.println("custProductList=="+custProductList);
    customerProduct.setProductList(custProductList);
    
    
    return customerProduct;
	
}



private boolean isProduct(String productID, Product product) {
	// TODO Auto-generated method stub
	System.out.println("isProduct");
	if(product.getId().equalsIgnoreCase(productID)) {
		return true;
	}
	return false;
}

@GetMapping("{id}")
  public Mono<ResponseEntity<Customer>> getCustomerById(@PathVariable String id) {
    return customerService.getById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  

  @PostMapping
  public Mono<ResponseEntity<Customer>> createCustomer(@RequestBody @Valid Customer customer) {
	 Customer customerToCreate = customer.toBuilder().customerId(null).build();
    return customerService.create(customerToCreate)
        .map(newCustomer -> ResponseEntity.created(URI.create("/customer/" + newCustomer.getCustomerId())).body(newCustomer));
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<Customer>> updateCustomer(@PathVariable String id, @RequestBody @Valid Customer customer) {
    return customerService.update(id, customer)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
    return customerService.deleteById(id)
        .map(r -> ResponseEntity.ok().<Void>build())
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }
}
