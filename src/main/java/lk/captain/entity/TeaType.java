package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaType {
    private String teaTypeId;
    private String teaTypeName;
    private String teaTypeDesc;
    private double teaPerPrice;
}
