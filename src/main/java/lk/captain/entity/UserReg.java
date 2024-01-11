package lk.captain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserReg {
    private String userId;
    private String userName;
    private String userPass;
    private String userTele;
    private String userAddress;
    private String name;
    private String userEmail;
}
