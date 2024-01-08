package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaSupplier {
    private String supplierId;
    private String suppName;
    private String suppAddres;
    private String suppTele;
    private String suppGen;
}
