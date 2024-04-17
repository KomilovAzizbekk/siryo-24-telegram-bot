package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.siryo24bot.entity.template.AbsDate;

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
@Table(name = "subcategory")
public class Subcategory extends AbsDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_uz", unique = true, nullable = false)
    private String nameUz;

    @Column(name = "name_ru", unique = true, nullable = false)
    private String nameRu;

    @Column(name = "number", unique = true, nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}
