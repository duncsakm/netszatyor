package ws.ivi.dyndns.netszatyor.service;
import java.util.Optional;

import com.linuxense.javadbf.DBFReader;
import ws.ivi.dyndns.netszatyor.model.Product;
import ws.ivi.dyndns.netszatyor.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository repo) {
        this.productRepository = repo;
    }

    public void importFromDbf(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        DBFReader reader = new DBFReader(inputStream);

        Object[] row;
        while ((row = reader.nextRecord()) != null) {
            String cikkszam = row[0].toString().trim();

            // meglévő termék keresése
            Optional<Product> optionalProduct = productRepository.findById(cikkszam);

            String nev = row[1].toString().trim();
            BigDecimal ar = new BigDecimal(row[2].toString());
            int keszlet = Integer.parseInt(row[3].toString());

            if (optionalProduct.isPresent()) {
                // ha már létezik → csak frissítjük a mezőket, ha változás van
                Product existing = optionalProduct.get();
                boolean modified = false;

                if (!existing.getNev().equals(nev)) {
                    existing.setNev(nev);
                    modified = true;
                }
                if (existing.getAr().compareTo(ar) != 0) {
                    existing.setAr(ar);
                    modified = true;
                }
                if (existing.getKeszlet() != keszlet) {
                    existing.setKeszlet(keszlet);
                    modified = true;
                }

                if (modified) {
                    productRepository.save(existing);
                }

            } else {
                // új termék beszúrása
                Product p = new Product(cikkszam, nev, ar, keszlet);
                productRepository.save(p);
            }
        }
    }

}