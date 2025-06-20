package ws.ivi.dyndns.netszatyor.service;

import com.linuxense.javadbf.DBFReader;
import ws.ivi.dyndns.netszatyor.model.Ckt;
import ws.ivi.dyndns.netszatyor.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository repo) {
        this.productRepository = repo;
    }

    public void importFromDbf(InputStream inputStream) throws Exception {
        DBFReader reader = new DBFReader(inputStream);

        int fieldCount = reader.getFieldCount();

        // Térkép: mezőnevek -> index
        Map<String, Integer> fieldIndexMap = new HashMap<>();
        for (int i = 0; i < fieldCount; i++) {
            fieldIndexMap.put(reader.getField(i).getName().toUpperCase(), i);
        }

        Object[] row;
        while ((row = reader.nextRecord()) != null) {
            String cktkod = row[fieldIndexMap.get("CKTKOD")].toString().trim();

            Optional<Ckt> optionalCkt = productRepository.findById(cktkod);

            String cktnev = row[fieldIndexMap.get("CKTNEV")].toString().split("@")[0].trim();
            String cktcsp = row[fieldIndexMap.get("CKTCSP")].toString().trim();
            String cktcsa = row[fieldIndexMap.get("CKTCSA")].toString().trim();
            Integer cktafa = Integer.parseInt(row[fieldIndexMap.get("CKTAFA")].toString());
            BigDecimal cktsar = new BigDecimal(row[fieldIndexMap.get("CKTSAR")].toString());
            BigDecimal cktfar = new BigDecimal(row[fieldIndexMap.get("CKTFAR")].toString());
            BigDecimal cktkem = new BigDecimal(row[fieldIndexMap.get("CKTKEM")].toString());
            BigDecimal cktakc = new BigDecimal(row[fieldIndexMap.get("CKTAKC")].toString());

            if (optionalCkt.isPresent()) {
                Ckt existing = optionalCkt.get();
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
                if (existing.getCktakc() == null || existing.getCktakc().compareTo(cktakc) != 0) {
                    existing.setCktakc(cktakc);
                    modified = true;
                }

                if (modified) {
                    productRepository.save(existing);
                }

            } else {
                Ckt ckt = Ckt.builder()
                        .cktkod(cktkod)
                        .cktnev(cktnev)
                        .cktcsp(cktcsp)
                        .cktcsa(cktcsa)
                        .cktafa(cktafa)
                        .cktsar(cktsar)
                        .cktfar(cktfar)
                        .cktkem(cktkem)
                        .cktakc(cktakc)
                        .build();

                productRepository.save(ckt);
            }
        }
    }
}
