package lk.captain.dto.tm;


import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PaymentSupplierTM {
    private String transctionId;
    private String transactionDate ;
    private double netTotal ;
    private double netWeight;
    private String suppName ;
    private Button btn;
  //  private String EmpName;
}
