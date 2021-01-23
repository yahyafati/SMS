package com.yahya.growth.stockmanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userProfile_id")
    private int id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userProfile_user_id")
    private User user;

    @Column(name = "userProfile_creationTime")
    private Timestamp creationTime;

    @Column(name = "userProfile_firstName")
    private String firstName;
    @Column(name = "userProfile_lastName")
    private String lastName;
    @Column(name = "userProfile_email", nullable = false, unique = true)
    private String email;
    @Column(name = "userProfile_phone")
    private String phone;
    @Column(name = "userProfile_dateOfBirth")
    private LocalDate dateOfBirth;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}
