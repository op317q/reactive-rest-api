package dev.iseif.reactiverestapi.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

//@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Customer {

  @EqualsAndHashCode.Exclude
  private String customerId;

  @NotBlank(message = "'customerName' is required")
  private String customerName;
  
  @NotBlank(message = "'address' is required")
  private String address;
  
  private List<String> productIdList ;
  

}
