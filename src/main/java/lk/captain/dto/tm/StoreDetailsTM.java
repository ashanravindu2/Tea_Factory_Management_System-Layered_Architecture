package lk.captain.dto.tm;

import javafx.scene.control.Button;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class StoreDetailsTM {
    private String wareHouseId;
    private String teaTypeId;
    private String date;
    private double quantity;
    private Button btn;
}
