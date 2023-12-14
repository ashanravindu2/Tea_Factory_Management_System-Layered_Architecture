package lk.captain.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CartTM {
    private String date;
    private double useTea;
    private Button btn;
}

