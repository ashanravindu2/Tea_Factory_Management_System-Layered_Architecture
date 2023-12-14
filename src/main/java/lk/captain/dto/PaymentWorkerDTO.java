package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentWorkerDTO {
    private String transctionId;
    private String transactionDate ;
    private int workCount ;
    private String workName ;
    private double extraSalary;
    private double netTotal ;
}
