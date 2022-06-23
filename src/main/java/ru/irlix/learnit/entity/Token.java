package ru.irlix.learnit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "token")
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_data_id", nullable = false, unique = true)
    private UserData userData;

    @Column(name = "access_token", nullable = false, unique = true, length = 512)
    private String accessToken;

    @Column(name = "refresh_token", nullable = false, unique = true, length = 512)
    private String refreshToken;

    @Column(name = "access_token_expires", nullable = false)
    private Instant accessTokenExpires;

    @Column(name = "refresh_token_expires", nullable = false)
    private Instant refreshTokenExpires;
}