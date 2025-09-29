package com.cherniak.simpleuserservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.Array2DHashSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profiles")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfileAddress> profileAddresses;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Profile(String firstName, String lastName, String phoneNumber, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public void addAddress(ProfileAddress address) {
        if (this.profileAddresses == null) {
            profileAddresses = new HashSet<>();
        }
        profileAddresses.add(address);
        address.setProfile(this);
    }
}
