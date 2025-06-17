package ws.ivi.dyndns.netszatyor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ws.ivi.dyndns.netszatyor.model.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findTop9ByOrderByArAsc();
}

