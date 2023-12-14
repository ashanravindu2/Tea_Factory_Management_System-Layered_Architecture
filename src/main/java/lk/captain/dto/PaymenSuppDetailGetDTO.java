package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymenSuppDetailGetDTO {
    private String transctionId;
    private String transactionDate ;
    private double netTotal ;
    private double netWeight;
    private String suppName ;
}
