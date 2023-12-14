package lk.captain.dto.tm;

import javafx.scene.control.Button;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderCartTM {
    private String teaTypeName;
    private String teaTypeId;
    private double quantity;
    private double unitPrice;
    private double total;
    private Button btn;
}
