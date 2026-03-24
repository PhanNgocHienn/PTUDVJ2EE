package ptudvj2ee.bai5_qlsp_jpa.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptudvj2ee.bai5_qlsp_jpa.model.CartItem;
import ptudvj2ee.bai5_qlsp_jpa.model.Order;
import ptudvj2ee.bai5_qlsp_jpa.model.OrderDetail;
import ptudvj2ee.bai5_qlsp_jpa.model.Product;
import ptudvj2ee.bai5_qlsp_jpa.repository.OrderDetailRepository;
import ptudvj2ee.bai5_qlsp_jpa.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "orderDate"));
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public void placeOrder(Order order, List<CartItem> cartItems, long totalAmount) {
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            Product product = productService.getProductById((int)(long)item.getProductId());
            detail.setProduct(product);
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }
    }
}
