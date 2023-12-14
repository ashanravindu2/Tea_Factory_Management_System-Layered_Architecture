package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WareHouseDTO {
   private String wareHouseId;
   private String wareHouseName;
   private double stockCount;
}
