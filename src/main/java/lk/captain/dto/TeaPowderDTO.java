package lk.captain.dto;

import lk.captain.view.tm.CartTM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaPowderDTO {

    private List<CartTM> tmList = new ArrayList<>();

}
