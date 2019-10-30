package com.github.therycn.tyhallowinner.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ATHLETE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Athlete {

    @Id
    @Column(name = "ATH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ATH_USERNAME")
    private String username;

    @Column(name = "ATH_REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "ATH_ACCESS_TOKEN")
    private String accessToken;

    @Column(name = "ATH_TOKEN_EXPIRES_AT")
    private Date expiresAt;

    public void updateStravaCredentials(String refreshToken, String accessToken, Date expiresAt) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

}
