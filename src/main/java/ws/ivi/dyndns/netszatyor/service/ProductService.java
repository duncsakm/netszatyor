package ws.ivi.dyndns.netszatyor.service;

import com.linuxense.javadbf.DBFReader;
import ws.ivi.dyndns.netszatyor.model.Ckt;
import ws.ivi.dyndns.netszatyor.model.FeldolgozasStatusz;
import ws.ivi.dyndns.netszatyor.repository.ProductRepository;
import ws.ivi.dyndns.netszatyor.repository.FeldolgozasStatuszRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FeldolgozasStatuszRepository statuszRepository;

    public ProductService(ProductRepository productRepository,
                          FeldolgozasStatuszRepository statuszRepository) {
        this.productRepository = productRepository;
        this.statuszRepository = statuszRepository;
    }

    @Async
    public void importFromDbf(InputStream inputStream, String filename) throws Exception {
        System.out.println(">>> [INDÍTÁS] Feldolgozás elkezdődött: " + filename);
        updateStatusz(filename, 0, 0, 0, false);

        DBFReader reader = new DBFReader(inputStream);
        reader.setCharactersetName("Cp852");

        int fieldCount = reader.getFieldCount();
        Map<String, Integer> fieldIndexMap = new HashMap<>();
        for (int i = 0; i < fieldCount; i++) {
            fieldIndexMap.put(reader.getField(i).getName().toUpperCase(), i);
        }

        Object[] row;
        int total = 0;
        int uj = 0;
        int modositott = 0;

        long lastUpdate = System.currentTimeMillis();

        while ((row = reader.nextRecord()) != null) {
            total++;

            String cktkod = row[fieldIndexMap.get("CKTKOD")].toString().trim();
            Optional<Ckt> optionalCkt = productRepository.findById(cktkod);

            String cktnev = row[fieldIndexMap.get("CKTNEV")].toString().split("@")[0].trim();
            String cktcsp = row[fieldIndexMap.get("CKTCSP")].toString().trim();
            String cktcsa = row[fieldIndexMap.get("CKTCSA")].toString().trim();

            // Javított sor – lebegőpontosból egész szám
            int cktafa = (int) Double.parseDouble(row[fieldIndexMap.get("CKTAFA")].toString());

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
                    modositott++;
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
                uj++;
            }

            long now = System.currentTimeMillis();
            if (now - lastUpdate > 10_000) {
                updateStatusz(filename, total, uj, modositott, false);
                lastUpdate = now;
            }
        }

        updateStatusz(filename, total, uj, modositott, true); // végső állapot
        System.out.println(">>> [KÉSZ] Feldolgozás véget ért: " + filename);
    }

    private void updateStatusz(String filename, int feldolgozott, int uj, int modositott, boolean kesz) {
        FeldolgozasStatusz statusz = FeldolgozasStatusz.builder()
                .fajlNev(filename)
                .feldolgozottSor(feldolgozott)
                .ujRekord(uj)
                .modositottRekord(modositott)
                .kesz(kesz)
                .frissitve(LocalDateTime.now())
                .build();

        statuszRepository.save(statusz);
        System.out.printf(">>> [MENTÉS] Állapot mentve – feldolgozott: %d, új: %d, módosított: %d, kész: %s%n",
                feldolgozott, uj, modositott, kesz ? "igen" : "nem");
    }
}
