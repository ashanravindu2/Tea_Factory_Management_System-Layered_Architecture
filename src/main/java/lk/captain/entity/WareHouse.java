package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WareHouse {
    private String wareHouseId;
    private String wareHouseName;
    private double stockCount;
}
