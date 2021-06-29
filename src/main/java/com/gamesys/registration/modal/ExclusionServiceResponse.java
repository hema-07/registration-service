package com.gamesys.registration.modal;

import com.gamesys.registration.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExclusionServiceResponse {

    private List<User> users;

}
