package io.ggammu.realspringbootjpa.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("B")
public class Book extends Item {

    private String author;

    private String isbn;

}
