package com.example.bai4.service;

import com.example.bai4.model.Category;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private List<Category> listCategory = new ArrayList<>();

    public CategoryService() {
        // Khởi tạo một số dữ liệu mẫu
        listCategory.add(new Category(1, "Điện thoại"));
        listCategory.add(new Category(2, "Máy tính bảng"));
        listCategory.add(new Category(3, "Phụ kiện"));
    }

    public List<Category> getAll() {
        return listCategory;
    }

    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Category category) {
        int maxId = listCategory.stream()
                .mapToInt(Category::getId)
                .max()
                .orElse(0);
        category.setId(maxId + 1);
        listCategory.add(category);
    }

    public void update(Category category) {
        Category find = get(category.getId());
        if (find != null) {
            find.setName(category.getName());
        }
    }

    public void delete(int id) {
        listCategory.removeIf(c -> c.getId() == id);
    }
}