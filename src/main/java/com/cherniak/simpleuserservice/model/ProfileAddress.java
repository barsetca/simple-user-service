package com.cherniak.simpleuserservice.model;

import com.cherniak.simpleuserservice.model.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "profile_addresses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProfileAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String country;
    @Column(nullable = false)
    @NotBlank
    private String city;
    private String street;
    @Column(name = "zip_code")
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AddressType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;
}
