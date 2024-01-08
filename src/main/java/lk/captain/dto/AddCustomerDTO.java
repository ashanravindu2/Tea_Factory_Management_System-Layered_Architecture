package lk.captain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddCustomerDTO{
    private String cusId;
    private String cusName;
    private String cusTele;
    private String cusAddress;
}
