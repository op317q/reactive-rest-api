package dev.iseif.reactiverestapi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import dev.iseif.reactiverestapi.model.Customer;
import dev.iseif.reactiverestapi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepostorImpl implements CustomerRepositor {
	
	 private final Map<Integer, Customer> customertMap = new ConcurrentHashMap<>();

	    public CustomerRepostorImpl() {
	    	
	    	/*Product prod1 = Product.builder().title("Apple iPhone XS Max").description("New iPhone XS Max").price(1099.99).id("1").build();
	    	Product prod2 = Product.builder().title("Apple MacBook Pro").description("New MacBook").price(2599.99).id("2").build();
	    	Product prod3 = Product.builder().title("Samsung Galaxy S10+").description("New Galaxy!!").price(799.99).id("3").build();
	    	List<Product> prod1List= new ArrayList<Product>();
	    	List<Product> prod2List= new ArrayList<Product>();
	    	List<Product> prod3List= new ArrayList<Product>();
	    	prod1List.add(prod1);
	    	prod1List.add(prod2);
	    	prod2List.add(prod2);
	    	prod2List.add(prod3);
	    	prod3List.add(prod3);
	    	prod3List.add(prod1);*/
	    	
	    	List<String> prod1List= new ArrayList<String>();
	    	List<String> prod2List= new ArrayList<String>();
	    	List<String> prod3List= new ArrayList<String>();
	    	prod1List.add("1");
	    	prod1List.add("2");
	    	prod2List.add("2");
	    	prod2List.add("3");
	    	prod3List.add("3");
	    	prod3List.add("1");
	    	
	        this.customertMap.put(1, Customer.builder().customerName("Cia").address("plaza drive, Woodbridge, New Jersey").customerId("1").productIdList(prod1List).build());

	        this.customertMap.put(2, Customer.builder().customerName("Alex").address("ritz plaza, Freehold, New Jersey").customerId("2").productIdList(prod2List).build());
	    
	        this.customertMap.put(3, Customer.builder().customerName("Jhon").address("South Laurel Ave, Middletown, New Jersey").customerId("3").productIdList(prod3List).build());
	 	   
	    }

		@Override
		public <S extends Customer> Mono<S> save(S entity) {
			// TODO Auto-generated method stub
			
			int id = customertMap.size() + 1;
			entity.setCustomerId(id+"");
			customertMap.put(id,entity);
			System.out.println(customertMap);
	        return Mono.empty();
		}

		@Override
		public Mono<Customer> findById(String customerId) {
			// TODO Auto-generated method stub
			System.out.println("customerId=="+customerId);
			System.out.println("customertMap="+customertMap);
			return Mono.just(customertMap.get(Integer.parseInt(customerId)));
		}

		@Override
		public Mono<List<Customer>> findAll() {
			// TODO Auto-generated method stub
			System.out.println(this.customertMap.values());
			List<Customer> custList=new ArrayList<Customer>();
			custList.addAll(this.customertMap.values());
			return Mono.just(custList);
		}

		@Override
		public Mono<Void> delete(Customer entity) {
			// TODO Auto-generated method stub
			customertMap.remove(Integer.parseInt(entity.getCustomerId()));
			System.out.println(this.customertMap);
			return Mono.empty();
		}

		@Override
		public Flux<?> deleteAll() {
			// TODO Auto-generated method stub
			customertMap.clear();
			return Flux.empty();
		}


	


	
}
