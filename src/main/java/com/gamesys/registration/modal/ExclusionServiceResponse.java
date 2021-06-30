package com.gamesys.registration.modal;

import com.gamesys.registration.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExclusionServiceResponse {

    private List<User> users;

}
