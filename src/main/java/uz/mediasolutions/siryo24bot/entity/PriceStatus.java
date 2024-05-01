package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.siryo24bot.entity.template.AbsLong;
import uz.mediasolutions.siryo24bot.enums.PriceStatusName;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "price_status")
public class PriceStatus extends AbsLong {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private PriceStatusName name;

}
