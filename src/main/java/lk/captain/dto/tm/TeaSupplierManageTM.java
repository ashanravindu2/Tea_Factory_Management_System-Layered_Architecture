package lk.captain.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class TeaSupplierManageTM {
    private String supplierId;
    private String suppName;
    private String suppAddres;
    private String suppTele;
    private String suppGen;
}
