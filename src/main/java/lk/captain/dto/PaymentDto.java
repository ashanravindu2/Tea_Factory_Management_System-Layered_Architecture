package lk.captain.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDto {
    private String transctionId;
    private String EmpId ;
    private String startTime ;
    private String endDate ;
    private String transactionDate ;
    private double netTotal ;
    private double netWeight;
    private double fertilizerReduced;
    private int workCount;
    private double extraSalary;

}
