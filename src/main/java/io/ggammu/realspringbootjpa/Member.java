package io.ggammu.realspringbootjpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

}
