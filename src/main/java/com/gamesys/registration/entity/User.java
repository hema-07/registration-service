package com.gamesys.registration.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FIRST_NAME")
    private String userFirstName;

    @Column(name = "LAST_NAME")
    private String userLastName;

    @Column(name = "DATE_OF_BIRTH")
    private String userDateOfBirth;

    @Column(name = "EMAIL")
    private String userEmail;

    @Column(name = "COUNTRY")
    private String userCountry;

    @Column(name = "STATUS")
    private String userStatus;

}
