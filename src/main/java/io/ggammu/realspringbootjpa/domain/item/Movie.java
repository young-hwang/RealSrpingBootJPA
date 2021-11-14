package io.ggammu.realspringbootjpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("M")
public class Movie extends Item {

    private String director;

    private String actor;

}
