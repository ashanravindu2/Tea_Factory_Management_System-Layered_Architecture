package lk.captain.dto.tm;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AttendenceTM {
    private String EmpAttenId;
    private String name;
    private int attMark;
    private String date;
    private String time;

}
