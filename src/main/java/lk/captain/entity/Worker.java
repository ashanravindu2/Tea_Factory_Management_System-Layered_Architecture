package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Worker {
    private String workId;
    private String workName;
    private String workAdress;
    private int workAge;
    private String workTele;
    private String workGen;
    private String workJoin;
    private String workBirth;
}
