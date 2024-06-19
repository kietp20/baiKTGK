package com.example.PTK_KTGK.Controller;

import com.example.PTK_KTGK.Model.Product;
import com.example.PTK_KTGK.Model.ProductImages;
import com.example.PTK_KTGK.Service.CategoryService;
import com.example.PTK_KTGK.Service.ProductImaesService;
import com.example.PTK_KTGK.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/products")

public class ProductController {

    public static String UPLOAD_DIRECTORY = "src/main/resources/static/images/";

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductImaesService productImaesService;
    // Display a list of all products
    @GetMapping
    public String showProductList(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("productImages",productImaesService.getAllProductsImages() );
        return "/products/product-list";
    }
    // For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories()); //Load categories
        return "/products/add-product";
    }
    // Process the form for adding a new product
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result, Model model, @RequestParam("image") MultipartFile file) {
        if (result.hasErrors()) {
            return "/products/add-product";
        }
        try {
            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
            Files.write(path, bytes);
            // Lưu tên hình ảnh vào trường imageName
            product.setImages(fileName);


            ProductImages pi = new ProductImages();
            pi.setProductid(product); // Gán giá trị ID
            pi.setImages(fileName); // Gán giá trị tên file hình ảnh
            productImaesService.addProductImage(pi);
            // Thêm hình ảnh vào danh sách
        } catch (IOException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
        }
        return "redirect:/products";
    }
    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid productId:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories()); // Load categories

        return "/products/update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid Product product,
                                BindingResult result, Model model, @RequestParam("image") MultipartFile file) {
        if (result.hasErrors()) {
            product.setId(id);
            return "/products/update-product";
        }
        try {
            Product pr = productService.getProductById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid productId:" + id));
            // Kiểm tra xem có file hình hay không
            if (!file.isEmpty()) {

                String fileName = file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
                Files.write(path, bytes);
                pr.setImages(fileName);

                ProductImages pi = new ProductImages();
                pi.setProductid(product); // Gán giá trị ID
                pi.setImages(fileName); // Gán giá trị tên file hình ảnh
                productImaesService.addProductImage(pi);


            }


            //System.out.println(pr.getImages());
            productService.updateProduct(product);
            //productService.updateProduct(pr);
        } catch (IOException e) {
            // Xử lý lỗi nếu có
            e.printStackTrace();
        }
        return "redirect:/products";
    }
    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProductById(id);

        return "redirect:/products";
    }

}
