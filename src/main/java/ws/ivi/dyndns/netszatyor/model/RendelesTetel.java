package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendelesTetel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Rendeles rendeles;

    @ManyToOne
    private Ckt termek;

    private int mennyiseg;

    private BigDecimal egysegar;
}
