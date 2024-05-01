package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.siryo24bot.entity.template.AbsLong;

import javax.persistence.*;
import java.sql.Timestamp;
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
@Table(name = "products")
public class Product extends AbsLong {

    @ManyToOne(fetch = FetchType.LAZY)
    private Seller seller;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Alternative> alternatives;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price_updated_time")
    private Timestamp priceUpdatedTime;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> analogs;

    @ManyToOne(fetch = FetchType.LAZY)
    private PriceStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Updates> updates;

}
