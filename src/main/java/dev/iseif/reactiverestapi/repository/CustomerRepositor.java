package dev.iseif.reactiverestapi.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import dev.iseif.reactiverestapi.model.Customer;
import dev.iseif.reactiverestapi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepositor   {
	
	public <S extends Customer> Mono<S> save(S entity) ;

	public Mono<Customer> findById(String customerId) ;


	public Mono<List<Customer>> findAll() ;

	public Mono<Void> delete(Customer entity) ;

	public Flux<?> deleteAll();

	
}
