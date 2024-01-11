package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaLeaf {
    private String supplierId;
    private String teaColecId;
    private double grosWeight;
    private double waterCon;
    private double netWeight;
    private String date;


}
