package io.ggammu.realspringbootjpa.domain.item;

import io.ggammu.realspringbootjpa.domain.item.Item;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("A")
public class Album extends Item {

    private String artists;

    private String etc;

}
