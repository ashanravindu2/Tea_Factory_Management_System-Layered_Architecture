package lk.captain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoreDetailsDTO {
            private String wareHouseId;
            private String teaTypeId;
            private String date;
            private double quantity;

}
