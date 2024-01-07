package lk.captain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddCustomer {
    private String cusId;
    private String cusName;
    private String cusTele;
    private String cusAddress;

}
