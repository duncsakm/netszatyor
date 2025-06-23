package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List; // <-- HIÁNYZOTT
import ws.ivi.dyndns.netszatyor.model.RendelesTetel; // <-- HIÁNYZOTT

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rendeles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime datum;

    private String nev;
    private String irsz;
    private String telepules;
    private String utca;
    private String hazszam;
    private String egyeb;

    private String telefon;
    private boolean ceg;
    private String adoszam;

    private String szall_irsz;
    private String szall_telepules;
    private String szall_utca;
    private String szall_hazszam;
    private String szall_egyeb;

    private BigDecimal vegosszeg;

    @Enumerated(EnumType.STRING)
    private FizetesiMod fizetesiMod;

    @OneToMany(mappedBy = "rendeles", cascade = CascadeType.ALL)
    private List<RendelesTetel> tetels;
}
