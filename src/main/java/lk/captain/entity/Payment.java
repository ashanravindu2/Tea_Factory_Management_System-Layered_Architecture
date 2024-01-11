package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
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
