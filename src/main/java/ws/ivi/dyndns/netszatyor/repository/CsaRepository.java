package ws.ivi.dyndns.netszatyor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ws.ivi.dyndns.netszatyor.model.Csa;

public interface CsaRepository extends JpaRepository<Csa, String> {
    // További lekérdezések szükség esetén ide jöhetnek
}
