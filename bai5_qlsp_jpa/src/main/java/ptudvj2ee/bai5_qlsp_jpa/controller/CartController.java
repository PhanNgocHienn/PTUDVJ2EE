package ptudvj2ee.bai5_qlsp_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ptudvj2ee.bai5_qlsp_jpa.model.CartItem;
import ptudvj2ee.bai5_qlsp_jpa.model.Product;
import ptudvj2ee.bai5_qlsp_jpa.service.CartService;
import ptudvj2ee.bai5_qlsp_jpa.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "cart/index";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Integer productId, @RequestParam("quantity") int quantity) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            CartItem item = new CartItem(product.getId(), product.getName(), product.getPrice(), quantity);
            cartService.add(item);
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        if (quantity <= 0) {
            cartService.remove(productId);
        } else {
            cartService.update(productId, quantity);
        }
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }
}
