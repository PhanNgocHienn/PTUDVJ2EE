package ptudvj2ee.bai5_qlsp_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ptudvj2ee.bai5_qlsp_jpa.model.Order;
import ptudvj2ee.bai5_qlsp_jpa.service.CartService;
import ptudvj2ee.bai5_qlsp_jpa.service.OrderService;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String checkoutForm(Model model) {
        if (cartService.getItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("order", new Order());
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/checkout";
    }

    @PostMapping
    public String processCheckout(Order order) {
        if (cartService.getItems().isEmpty()) {
            return "redirect:/cart";
        }
        orderService.placeOrder(order, cartService.getItems(), cartService.getTotal());
        cartService.clear();
        return "redirect:/checkout/success";
    }

    @GetMapping("/success")
    public String checkoutSuccess() {
        return "cart/success";
    }
}
