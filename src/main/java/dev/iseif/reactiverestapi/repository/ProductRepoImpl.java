package dev.iseif.reactiverestapi.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Repository;

import dev.iseif.reactiverestapi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepoImpl implements ProductRepository {
	
	 private final Map<Integer, Product> productMap = new ConcurrentHashMap<>();

	    public ProductRepoImpl() {
	        this.productMap.put(1, Product.builder().title("Apple iPhone XS Max").description("New iPhone XS Max").price(1099.99).id("1").build());

	        this.productMap.put(2, Product.builder().title("Apple MacBook Pro").description("New MacBook").price(2599.99).id("2").build());
	    
	        this.productMap.put(3, Product.builder().title("Samsung Galaxy S10+").description("New Galaxy!!").price(799.99).id("3").build());
	 	   
	    }

	

	@Override
	public <S extends Product> Mono<S> save(S entity) {
		// TODO Auto-generated method stub
		
		int id = productMap.size() + 1;
		entity.setId(id+"");
		productMap.put(id,entity);
		System.out.println(productMap);
        return Mono.empty();
	}


	@Override
	public Mono<Product> findById(String id) {
		// TODO Auto-generated method stub
		System.out.println("id=="+id);
		System.out.println("productMap="+productMap);
		return Mono.just(productMap.get(Integer.parseInt(id)));
	}


	
	@Override
	public Flux<Product> findAll() {
		// TODO Auto-generated method stub
		System.out.println(this.productMap.values());
		return Flux.fromIterable(this.productMap.values());
	}



	@Override
	public Mono<Void> delete(Product entity) {
		// TODO Auto-generated method stub
		productMap.remove(Integer.parseInt(entity.getId()));
		System.out.println(this.productMap);
		return Mono.empty();
	}



	@Override
	public Flux deleteAll() {
		// TODO Auto-generated method stub
		productMap.clear();
		return Flux.empty();
	}



	@Override
	public Flux<Product> findByTitleContainingIgnoreCase(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
