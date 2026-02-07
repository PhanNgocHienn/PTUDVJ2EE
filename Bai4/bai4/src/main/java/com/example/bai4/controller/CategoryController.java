package com.example.bai4.controller;

import com.example.bai4.model.Category;
import com.example.bai4.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị danh sách danh mục
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listCategories", categoryService.getAll());
        return "category/index";
    }

    // Trang thêm danh mục
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category/add";
        }
        categoryService.add(category);
        return "redirect:/categories";
    }

    // Trang sửa danh mục
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Category category = categoryService.get(id);
        if (category == null) return "error/404";
        model.addAttribute("category", category);
        return "category/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category/edit";
        }
        categoryService.update(category);
        return "redirect:/categories";
    }

    // Chức năng xóa danh mục
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}