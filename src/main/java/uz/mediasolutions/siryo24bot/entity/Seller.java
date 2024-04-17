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
@Table(name = "sellers")
public class Seller extends AbsLong {

    @Column(name = "organization")
    private String organization;

    @Column(name = "phone_number_1")
    private String phoneNumber1;

    @Column(name = "phone_number_2")
    private String phoneNumber2;

    @Column(name = "info", columnDefinition = "text")
    private String info;

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "channel")
    private String channel;

    @Column(name = "accept_cash")
    private String acceptCash;

    @Column(name = "accept_transfer")
    private String acceptTransfer;

}
