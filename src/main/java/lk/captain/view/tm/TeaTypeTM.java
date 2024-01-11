package lk.captain.view.tm;


import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TeaTypeTM {
    private String teaTypeId;
    private String teaTypeName;
    private String teaTypeDesc;
    private double teaPerPrice;
    private Button btn;
}
