package lk.captain.view.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.util.ArrayList;
import java.util.function.IntFunction;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderCartTM extends ArrayList<OrderCartTM> {
    private String teaTypeName;
    private String teaTypeId;
    private double quantity;
    private double unitPrice;
    private double total;
    private Button btn;
}
