package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ckt")
@Getter
@Setter
public class Ckt {

    @Id
    @Column(name = "cktuid")
    private String cktuid;

    @Column(name = "cktcik")
    private String cktcik;

    @Column(name = "cktmeg")
    private String cktmeg;

    @Column(name = "cktmer")
    private String cktmer;

    @Column(name = "cktfar")
    private Double cktfar;

    @Column(name = "cktar4")
    private Double cktar4;

    @Column(name = "cktle2")
    private String cktle2;
}
