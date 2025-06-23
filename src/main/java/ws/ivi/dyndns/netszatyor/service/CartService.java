package ws.ivi.dyndns.netszatyor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ws.ivi.dyndns.netszatyor.model.CartItem;
import ws.ivi.dyndns.netszatyor.model.Ckt;

import java.math.BigDecimal;
import java.util.*;

@Service
@SessionScope
public class CartService {

    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void addItem(Ckt product, int quantity) {
        String id = product.getCktkod();
        if (items.containsKey(id)) {
            CartItem existing = items.get(id);
            existing.setMennyiseg(existing.getMennyiseg() + quantity);
        } else {
            CartItem newItem = new CartItem(product, quantity);
            items.put(id, newItem);
        }
    }

    public void updateQuantity(String productId, int quantity) {
        if (items.containsKey(productId)) {
            if (quantity <= 0) {
                items.remove(productId);
            } else {
                items.get(productId).setMennyiseg(quantity);
            }
        }
    }

    public void removeItem(String productId) {
        items.remove(productId);
    }

    public void clearCart() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(CartItem::getOsszesen)  // <-- JAVÍTÁS
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
