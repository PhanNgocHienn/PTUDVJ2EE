package ptudvj2ee.bai5_qlsp_jpa.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;
import ptudvj2ee.bai5_qlsp_jpa.model.CartItem;

import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class CartService {
    private List<CartItem> items = new ArrayList<>();

    public void add(CartItem item) {
        for (CartItem existingItem : items) {
            if (existingItem.getProductId().equals(item.getProductId())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void remove(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public void update(Long productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public long getTotal() {
        return items.stream()
                .mapToLong(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
