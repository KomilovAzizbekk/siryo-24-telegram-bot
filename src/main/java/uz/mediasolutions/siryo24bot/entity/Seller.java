package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.siryo24bot.entity.template.AbsDate;
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
public class Seller extends AbsDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization", nullable = false)
    private String organization;

    @Column(name = "phone_number_1", nullable = false)
    private String phoneNumber1;

    @Column(name = "phone_number_2")
    private String phoneNumber2;

    @Column(name = "chatId")
    private String chatId;

    @Column(name = "info", columnDefinition = "text")
    private String info;

    @Column(name = "active")
    private boolean active;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "accept_cash")
    private boolean acceptCash;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> products;

    @Column(name = "stock_market")
    private boolean stockMarket;

    @Column(name = "accept_transfer")
    private boolean acceptTransfer;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FavouriteProducts> favouriteProducts;

}
