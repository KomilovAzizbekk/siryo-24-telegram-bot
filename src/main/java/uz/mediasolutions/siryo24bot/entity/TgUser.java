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
@Table(name = "tg_users")
public class TgUser extends AbsLong {

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "banned")
    private boolean banned;

    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    private Step step;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products;

}
