package lk.captain.dto.tm;


import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TeaLeafEntryTM {
    private String supplierId;
    private double grosWeight;
    private double waterCon;
    private double netWeight;
    private String date;
    private Button btn;

}
