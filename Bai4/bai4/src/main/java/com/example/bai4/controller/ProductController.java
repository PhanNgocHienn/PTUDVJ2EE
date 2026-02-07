package com.example.bai4.controller;

import com.example.bai4.model.Product;
import com.example.bai4.service.ProductService;
import com.example.bai4.service.CategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String Index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String Create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String Create(@Valid Product newProduct,
            BindingResult result,
            @RequestParam("category.id") int categoryId,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", newProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        productService.updateImage(newProduct, imageProduct);
        newProduct.setCategory(categoryService.get(categoryId));
        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String Edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) {
            return "error/404";
        }
        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String Edit(@Valid Product editProduct,
            BindingResult result,
            @RequestParam("category.id") int categoryId, // Thêm dòng này để nhận ID danh mục
            @RequestParam("imageProduct") MultipartFile imageProduct,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        // Thiết lập đối tượng Category cho Product trước khi update
        editProduct.setCategory(categoryService.get(categoryId));

        if (imageProduct != null && !imageProduct.isEmpty()) {
            productService.updateImage(editProduct, imageProduct);
        }

        productService.update(editProduct);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        Product find = productService.get(id);
        if (find != null) {
            productService.delete(id);
        }
        return "redirect:/products";
    }

}