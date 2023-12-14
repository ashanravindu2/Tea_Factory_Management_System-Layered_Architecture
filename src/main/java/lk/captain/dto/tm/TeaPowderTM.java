package lk.captain.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TeaPowderTM {
    private String date;
    private double useTea;
    private Button btn;

}
