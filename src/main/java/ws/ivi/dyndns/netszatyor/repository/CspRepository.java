package ws.ivi.dyndns.netszatyor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ws.ivi.dyndns.netszatyor.model.Csp;

public interface CspRepository extends JpaRepository<Csp, String> {
    // További lekérdezések szükség esetén ide jöhetnek
}
