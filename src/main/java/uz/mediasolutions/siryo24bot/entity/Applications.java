package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.siryo24bot.entity.template.AbsLong;

import javax.persistence.*;
import java.util.List;

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
@Table(name = "applications")
public class Applications extends AbsLong {

    @ManyToOne(fetch = FetchType.LAZY)
    private TgUser user;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

}
