package lk.captain.dto;

import lk.captain.dto.tm.OrderCartTM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaSellsDTO {
    private String orderId;
    private String cusId;
    private String date;
    private String teaTypeName;
    private String time;
    private List<OrderCartTM> tmList = new ArrayList<>();

}
