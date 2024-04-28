package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.siryo24bot.entity.template.AbsLong;

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
@Table(name = "updates")
public class Updates extends AbsLong {

    @Column(name = "price")
    private Integer price;

    @Column(name = "updated_time")
    private String updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
