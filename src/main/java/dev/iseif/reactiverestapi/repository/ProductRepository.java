package dev.iseif.reactiverestapi.repository;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import dev.iseif.reactiverestapi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository   {
	
	public <S extends Product> Mono<S> save(S entity) ;

	public Mono<Product> findById(String id) ;


	public Flux<Product> findAll() ;

	public Mono<Void> delete(Product entity) ;

	public Flux deleteAll();

	public Flux<Product> findByTitleContainingIgnoreCase(String title);

	
}
