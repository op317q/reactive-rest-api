package dev.iseif.reactiverestapi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
public class Product {

  @EqualsAndHashCode.Exclude
  private String id;

  @NotBlank(message = "'title' is required")
  private String title;

  @NotBlank(message = "'description' is required")
  private String description;

  @NotNull(message = "'price' is required")
  @Positive(message = "'price' must be greater than zero")
  private Double price;
}
