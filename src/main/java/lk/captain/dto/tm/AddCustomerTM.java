package lk.captain.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AddCustomerTM {
    private String cusId;
    private String cusName;
    private String cusTele;
    private String cusAddress;
}
