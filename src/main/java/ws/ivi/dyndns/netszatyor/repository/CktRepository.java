package ws.ivi.dyndns.netszatyor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ws.ivi.dyndns.netszatyor.model.Ckt;

import java.util.List;

public interface CktRepository extends JpaRepository<Ckt, String> {

    @Query("SELECT c FROM Ckt c WHERE c.cktakc IS NOT NULL ORDER BY function('random')")
    List<Ckt> findRandom9Akcios();

    @Query("SELECT c FROM Ckt c " +
            "WHERE (:focsoport IS NULL OR c.cktcsp = :focsoport) " +
            "AND (:alcsoport IS NULL OR c.cktcsa = :alcsoport) " +
            "AND (:keres IS NULL OR LOWER(c.cktnev) LIKE LOWER(CONCAT('%', :keres, '%')))")
    Page<Ckt> findByFilters(
            @Param("keres") String keres,
            @Param("focsoport") String focsoport,
            @Param("alcsoport") String alcsoport,
            Pageable pageable
    );

    @Query(value = "SELECT * FROM ckt ORDER BY random() LIMIT 9", nativeQuery = true)
    List<Ckt> findRandom9();
}
