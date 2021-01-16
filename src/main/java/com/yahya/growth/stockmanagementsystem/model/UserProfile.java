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
    private int id;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private String firstName;
    private Timestamp creationTime;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    private LocalDate dateOfBirth;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

}
