package dev.iseif.reactiverestapi.model;


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
public class Message {

  @EqualsAndHashCode.Exclude
  private String id;

  @NotBlank(message = "'message' is required")
  private String message;

}
