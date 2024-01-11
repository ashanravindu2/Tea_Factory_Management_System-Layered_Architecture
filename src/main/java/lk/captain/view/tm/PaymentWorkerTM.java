package lk.captain.view.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PaymentWorkerTM {
    private String transctionId;
    private String transactionDate ;
    private int workCount ;
    private String workName ;
    private double extraSalary;
    private double netTotal ;
    private Button btn;
}
