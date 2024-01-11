package lk.captain.entity;

import lk.captain.view.tm.OrderCartTM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaSells {
    private String orderId;
    private String cusId;
    private String date;
    private String teaTypeName;
    private String time;
    private List<OrderCartTM> tmList = new ArrayList<>();
}
