package ws.ivi.dyndns.netszatyor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ws.ivi.dyndns.netszatyor.model.Ckt;

import java.util.List;

public interface ProductRepository extends JpaRepository<Ckt, String> {

    // Véletlenszerű 9 akciós termék lekérdezése (ahol cktakc < cktfar)
    @Query(value = "SELECT * FROM ckt WHERE cktakc < cktfar ORDER BY RANDOM() LIMIT 9", nativeQuery = true)
    List<Ckt> findRandom9Akcios();
}
