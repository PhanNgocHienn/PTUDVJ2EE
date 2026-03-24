package ptudvj2ee.bai5_qlsp_jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ptudvj2ee.bai5_qlsp_jpa.model.Product;
import ptudvj2ee.bai5_qlsp_jpa.repository.CategoryRepository;
import ptudvj2ee.bai5_qlsp_jpa.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> searchProducts(String keyword, Integer categoryId, Pageable pageable) {
        return productRepository.searchProducts(keyword, categoryId, pageable);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}