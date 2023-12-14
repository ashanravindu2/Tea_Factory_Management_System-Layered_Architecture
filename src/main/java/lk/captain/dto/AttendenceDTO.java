package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendenceDTO {
    private String EmpAttenId;
    private String name;
    private int attMark;
    private String date;
    private String time;

}
