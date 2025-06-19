package ws.ivi.dyndns.netszatyor.service;

import com.linuxense.javadbf.DBFReader;
import ws.ivi.dyndns.netszatyor.model.Product;
import ws.ivi.dyndns.netszatyor.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;

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
            String cktkod = row[0].toString().trim();

            Optional<Product> optionalProduct = productRepository.findById(cktkod);

            String cktnev = row[1].toString().split("@")[0].trim();
            String cktcsp = row[2].toString().trim();
            String cktcsa = row[3].toString().trim();
            Integer cktafa = Integer.parseInt(row[4].toString());
            BigDecimal cktsar = new BigDecimal(row[5].toString());
            BigDecimal cktfar = new BigDecimal(row[6].toString());
            BigDecimal cktkem = new BigDecimal(row[7].toString());

            if (optionalProduct.isPresent()) {
                Product existing = optionalProduct.get();
                boolean modified = false;

                if (!existing.getCktnev().equals(cktnev)) {
                    existing.setCktnev(cktnev);
                    modified = true;
                }
                if (!existing.getCktcsp().equals(cktcsp)) {
                    existing.setCktcsp(cktcsp);
                    modified = true;
                }
                if (!existing.getCktcsa().equals(cktcsa)) {
                    existing.setCktcsa(cktcsa);
                    modified = true;
                }
                if (!existing.getCktafa().equals(cktafa)) {
                    existing.setCktafa(cktafa);
                    modified = true;
                }
                if (existing.getCktsar().compareTo(cktsar) != 0) {
                    existing.setCktsar(cktsar);
                    modified = true;
                }
                if (existing.getCktfar().compareTo(cktfar) != 0) {
                    existing.setCktfar(cktfar);
                    modified = true;
                }
                if (existing.getCktkem().compareTo(cktkem) != 0) {
                    existing.setCktkem(cktkem);
                    modified = true;
                }

                if (modified) {
                    productRepository.save(existing);
                }

            } else {
                Product p = Product.builder()
                        .cktkod(cktkod)
                        .cktnev(cktnev)
                        .cktcsp(cktcsp)
                        .cktcsa(cktcsa)
                        .cktafa(cktafa)
                        .cktsar(cktsar)
                        .cktfar(cktfar)
                        .cktkem(cktkem)
                        .build();

                productRepository.save(p);
            }
        }
    }
}
