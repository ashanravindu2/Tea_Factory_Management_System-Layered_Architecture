package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoreDetails {
            private String wareHouseId;
            private String teaTypeId;
            private String date;
            private double quantity;

}
