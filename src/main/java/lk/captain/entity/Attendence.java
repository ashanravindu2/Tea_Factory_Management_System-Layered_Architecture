package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attendence {
    private String EmpAttenId;
    private String name;
    private int attMark;
    private String date;
    private String time;
}
