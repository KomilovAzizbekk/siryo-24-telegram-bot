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

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subcategory subcategory;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Alternative> alternatives;

    @Column(name = "country")
    private String country;

    @Column(name = "manufacturer")
    private String manufacturer;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private Price price;

    @OneToOne(fetch = FetchType.LAZY)
    private FileImage image;

    private Timestamp updateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Product> analogs;

}
