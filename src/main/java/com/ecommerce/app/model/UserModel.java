package com.ecommerce.app.model;

import com.ecommerce.app.domain.User;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */



@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserModel  implements Serializable {

    private UUID userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String country;
    private String userType;

}
