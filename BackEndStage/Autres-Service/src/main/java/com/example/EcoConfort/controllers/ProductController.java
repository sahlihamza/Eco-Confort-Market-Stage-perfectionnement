package com.example.EcoConfort.controllers;

import com.example.EcoConfort.entity.Product;
import com.example.EcoConfort.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping("/addProduct/{categoryId}")
//    public ResponseEntity<Product> addProduct(@RequestBody Product product, @PathVariable Long categoryId) {
//        Product addedProduct = productService.addProduct(product, categoryId);
//        if (addedProduct != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }


    //hethy eli ekher wahda machyte biha
//    @PostMapping("/addProduct/{categoryId}")
//    public ResponseEntity<Product> addProduct(
//            @ModelAttribute Product product,
//            @PathVariable Long categoryId,
//            @RequestParam("imageFile") MultipartFile imageFile
//    ) {
//        System.out.println("Received image file: " + imageFile.getOriginalFilename());
//        Product addedProduct = productService.addProduct(product, categoryId, imageFile);
//        if (addedProduct != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
//        } else {
//            return ResponseEntity.badRequest().build(); // Handle the case where the category is not found
//        }
//    }

    @PostMapping("/addProduct/{categoryId}")
    public ResponseEntity<?> addProduct(
            @ModelAttribute Product product,
            @PathVariable Long categoryId,
            @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            System.out.println("Received image file: " + imageFile.getOriginalFilename());
            Product addedProduct = productService.addProduct(product, categoryId, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Failed to add product: " + e.getMessage());
        }
    }




//    @PostMapping(value = "/addProduct/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Product> addProduct(@RequestBody Product product,
//                                              @PathVariable Long categoryId,
//                                              @RequestParam("imageFile") MultipartFile imageFile) {
//        Product addedProduct = productService.addProduct(product, categoryId, imageFile);
//        if (addedProduct != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getProductsByIds/{productIds}")
    public ResponseEntity<List<Product>> getProductsByIds(@PathVariable List<Long> productIds) {
        List<Product> products = productService.getProductsByIds(productIds);

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product modifiedProduct = productService.updateProduct(id, updatedProduct);
        if (modifiedProduct != null) {
            return ResponseEntity.ok(modifiedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

//    @GetMapping("/byName/{productName}")
//    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String productName) {
//        List<Product> products = productService.getProductsByName(productName);
//        if (!products.isEmpty()) {
//            return ResponseEntity.ok(products);
//        } else {
//            return ResponseEntity.noContent().build();
//        }
//    }

    @GetMapping("/byName/{partialProductName}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String partialProductName) {
        List<Product> products = productService.getProductsByName(partialProductName);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/byBrand/{brand}")
    public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
        List<Product> products = productService.getProductsByBrand(brand);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/byPriceRange/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@PathVariable double minPrice, @PathVariable double maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/byDescendingPrice")
    public ResponseEntity<List<Product>> getProductsByDescendingPrice() {
        List<Product> products = productService.getProductsByDescendingPrice();
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/byAscendingPrice")
    public ResponseEntity<List<Product>> getProductsByAscendingPrice() {
        List<Product> products = productService.getProductsByAscendingPrice();
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/inStock")
    public ResponseEntity<List<Product>> getProductsInStock() {
        List<Product> productsInStock = productService.getProductsInStock();
        if (!productsInStock.isEmpty()) {
            return ResponseEntity.ok(productsInStock);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/outOfStock")
    public List<Product> getProductsOutOfStock() {
        return productService.getProductsOutOfStock();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalNumberOfProducts() {
        long totalProducts = productService.getTotalNumberOfProducts();
        return ResponseEntity.ok(totalProducts);
    }

    @GetMapping("/countInStock")
    public ResponseEntity<Long> getTotalProductsInStock() {
        long totalInStock = productService.getTotalProductsInStock();
        return ResponseEntity.ok(totalInStock);
    }
}
