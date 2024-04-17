package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.mediasolutions.siryo24bot.entity.template.AbsDate;

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
@Table(name = "category")
public class Category extends AbsDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_uz", nullable = false, unique = true)
    private String nameUz;

    @Column(name = "name_ru", nullable = false, unique = true)
    private String nameRu;

    @Column(name = "number", nullable = false, unique = true)
    private Integer number;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Subcategory> subcategories;

}
