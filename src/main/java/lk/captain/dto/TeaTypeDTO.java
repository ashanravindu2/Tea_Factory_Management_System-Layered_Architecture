package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaTypeDTO {
    private String teaTypeId;
    private String teaTypeName;
    private String teaTypeDesc;
    private double teaPerPrice;
}
