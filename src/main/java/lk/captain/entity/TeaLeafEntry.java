package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeaLeafEntry {
    private String supplierId;
    private String teaColecId;
    private double grosWeight;
    private double waterCon;
    private double netWeight;
    private String date;

}
