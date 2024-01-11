package lk.captain.view.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WoodMatirialTM {
    private String  barrelId;
    private String wCategory;
    private double  wWeight ;
    private Button btn;
}
