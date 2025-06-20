package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeldolgozasStatusz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fajlNev;

    private int osszesSor;

    private int feldolgozottSor;

    private int ujRekord;

    private int modositottRekord;

    private boolean kesz;

    private LocalDateTime frissitve;
}
