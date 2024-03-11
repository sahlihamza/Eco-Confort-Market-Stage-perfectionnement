package com.example.EcoConfort.services;

import com.example.EcoConfort.entity.Category;
import com.example.EcoConfort.entity.Product;
import com.example.EcoConfort.repository.CategoryRepository;
import com.example.EcoConfort.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;




@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

    }

//    public Product addProduct(Product product, Long categoryId) {
//        Optional<Category> category = categoryRepository.findById(categoryId);
//
//        if (category.isPresent()) {
//            product.setCategory(category.get());
//            return productRepository.save(product);
//        } else {
//            // Handle the case where the category is not found
//            return null;
//        }
//    }

//    public Product addProduct(Product product, Long categoryId, MultipartFile imageFile) {
//        Optional<Category> category = categoryRepository.findById(categoryId);
//
//        if (category.isPresent()) {
//            product.setCategory(category.get());
//
//            if (imageFile != null && !imageFile.isEmpty()) {
//                try {
//                    String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
//                    String uploadDir = "C:\\Users\\sahli\\Desktop\\images";
//
//                    File uploadPath = new File(uploadDir);
//                    if (!uploadPath.exists()) {
//                        uploadPath.mkdirs();
//                    }
//
//                    String filePath = uploadDir + File.separator + fileName;
//                    imageFile.transferTo(new File(filePath));
//
//                    System.out.println("Chemin d'accès de l'image : " + filePath);
//
//                    product.setImage(filePath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    // Gérer l'exception
//                }
//            }
//
//            return productRepository.save(product);
//        } else {
//            return null; // Gérer le cas où la catégorie n'est pas trouvée
//        }
//    }


    public Product addProduct(Product product, Long categoryId, MultipartFile imageFile) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (!category.isPresent()) {
            // Gérer le cas où la catégorie n'est pas trouvée
            throw new RuntimeException("Category not found with id " + categoryId);
        }

        product.setCategory(category.get());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                byte[] bytes = imageFile.getBytes();
                product.setImage(bytes);
            } catch (IOException e) {
                // Log l'exception ici
                e.printStackTrace();
                throw new RuntimeException("Failed to store image data");
            }
        }

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByIds(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setProductName(updatedProduct.getProductName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setImage(updatedProduct.getImage());
            product.setStockQuantity(updatedProduct.getStockQuantity());
            product.updateStockStatus();

            return productRepository.save(product);
        } else {
            // Handle the case where the product is not found
            return null;
        }
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

//    public List<Product> getProductsByName(String productName) {
//        return productRepository.findByProductName(productName);
//    }

    public List<Product> getProductsByName(String productName) {
        return productRepository.findByProductNameContaining(productName);
    }

    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrandContaining(brand);
    }
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> getProductsByDescendingPrice() {
        return productRepository.findByOrderByPriceDesc();
    }
    public List<Product> getProductsByAscendingPrice() {
        return productRepository.findByOrderByPriceAsc();
    }

    public List<Product> getProductsInStock() {
        return productRepository.findByInStockTrue();
    }
    public List<Product> getProductsOutOfStock() {
        return productRepository.findByInStockFalse();
    }


    public long getTotalNumberOfProducts() {
        return productRepository.count();
    }

    public long getTotalProductsInStock() {
        return productRepository.countByInStockTrue();
    }



}
