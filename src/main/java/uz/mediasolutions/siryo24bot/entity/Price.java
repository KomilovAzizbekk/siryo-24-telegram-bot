package uz.mediasolutions.siryo24bot.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "parse_time")
    private Timestamp parseTime; //HH:mm dd/MM/yyyy

    @OneToOne(mappedBy = "price", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Product product;

    public Price(Integer price, Timestamp parseTime) {
        this.price = price;
        this.parseTime = parseTime;
    }
}
