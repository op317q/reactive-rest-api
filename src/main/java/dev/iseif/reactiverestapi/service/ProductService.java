package dev.iseif.reactiverestapi.service;

import dev.iseif.reactiverestapi.model.Product;
import dev.iseif.reactiverestapi.repository.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ProductService {
	
  @Autowired 
  private ProductRepository productRepository;

 
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Mono<List<Product>> getAll() {
   
    
   // return Mono.defer(() -> {
    	 return productRepository.findAll();
	//  }).subscribeOn(Schedulers.elastic());
  }

  public Mono<Product> getById(String id) {
    return productRepository.findById(id);
  }

  public Flux<Product> searchByTitle(String title) {
    return productRepository.findByTitleContainingIgnoreCase(title);
  }

  public Mono<Product> create(Product product) {
    return productRepository.save(product);
  }

  public Mono<Product> update(String id, Product updatedProduct) {
    return productRepository.findById(id)
        .map(existingProduct -> existingProduct.toBuilder()
              .title(updatedProduct.getTitle())
              .description(updatedProduct.getDescription())
              .price(updatedProduct.getPrice())
              .build())
        .flatMap(productRepository::save);
  }

  public Mono<Product> deleteById(String id) {
    return productRepository.findById(id)
        .flatMap(product -> productRepository.delete(product).then(Mono.just(product)));
  }
}
