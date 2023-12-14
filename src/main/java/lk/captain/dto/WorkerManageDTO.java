package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkerManageDTO {

    private String workId;
    private String workName;
    private String workAdress;
    private int workAge;
    private String workTele;
    private String workGen;
    private String workJoin;
    private String workBirth;
}
