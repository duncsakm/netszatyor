package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {

    @Id
    private Long id; // Ugyanaz, mint a User ID

    @OneToOne
    @MapsId
    private User user;

    // Számlázási cím
    private String nev;
    private String iranyitoszam;
    private String telepules;
    private String utca;
    private String hazszam;
    private String cimegyeb;

    private String telefon;

    private boolean ceges;
    private String adoszam;

    // Kiszállítási cím (ha eltér)
    private String szall_iranyitoszam;
    private String szall_telepules;
    private String szall_utca;
    private String szall_hazszam;
    private String szall_egyeb;

    // Kényelmi getter a kiszállítási cím ellenőrzésére
    public boolean isKiszallitCim() {
        return szall_iranyitoszam != null || szall_telepules != null || szall_utca != null;
    }

    // Kiegészítő getterek — ha Lombok nem generálja megfelelően (pl. a build során)
    public String getNev() {
        return nev;
    }

    public String getIranyitoszam() {
        return iranyitoszam;
    }

    public String getTelepules() {
        return telepules;
    }

    public String getUtca() {
        return utca;
    }

    public String getHazszam() {
        return hazszam;
    }

    public String getCimegyeb() {
        return cimegyeb;
    }

    public String getTelefon() {
        return telefon;
    }

    public boolean isCeges() {
        return ceges;
    }

    public String getAdoszam() {
        return adoszam;
    }

    public String getSzall_iranyitoszam() {
        return szall_iranyitoszam;
    }

    public String getSzall_telepules() {
        return szall_telepules;
    }

    public String getSzall_utca() {
        return szall_utca;
    }

    public String getSzall_hazszam() {
        return szall_hazszam;
    }

    public String getSzall_egyeb() {
        return szall_egyeb;
    }
}
