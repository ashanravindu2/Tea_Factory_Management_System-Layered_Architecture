package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaLeafEntryDTO {
    private String supplierId;
    private String teaColecId;
    private double grosWeight;
    private double waterCon;
    private double netWeight;
    private String date;


}
