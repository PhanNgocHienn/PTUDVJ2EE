package com.example.bai4.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.bai4.model.Product;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() {
        return listProduct;
    }

    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);
        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());

            // Cập nhật danh mục mới
            find.setCategory(editProduct.getCategory());

            if (editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    public void updateImage(Product newProduct, MultipartFile imageProduct) {
        String contentType = imageProduct.getContentType();
        // Kiểm tra định dạng tệp (Logic trong ảnh có lỗi cú pháp, đây là bản sửa lỗi)
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Tệp tải lên không phải là hình ảnh!");
        }

        if (!imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }

                String newFileName = UUID.randomUUID().toString() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);

                newProduct.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(int id) {
    listProduct.removeIf(p -> p.getId() == id);
}
}