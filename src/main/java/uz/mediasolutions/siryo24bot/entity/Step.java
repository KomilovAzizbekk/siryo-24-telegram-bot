package uz.mediasolutions.siryo24bot.entity;

import lombok.*;
import uz.mediasolutions.siryo24bot.enums.StepName;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "step")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private StepName name;

}
