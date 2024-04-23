package com.example.crudspring.crudspring.controllers;

import com.example.crudspring.crudspring.domain.product.Product;
import com.example.crudspring.crudspring.domain.product.ProductRepository;
import com.example.crudspring.crudspring.domain.product.RequestProduct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/mercadoria")
@RestController
public class ProductController {
    @Autowired
    private ProductRepository repository;
    @GetMapping
    public ResponseEntity getAllProducts(){
        var allProducts = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        Product newProduct = new Product(data);
        System.out.println(data);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional //indica que esse put é uma transação, que será feito vários comandos sql e precisam ser iniciaos e ACABADOS
    public  ResponseEntity updateProduct(@RequestBody @Valid RequestProduct data){
        Optional<Product> optionalProduct = repository.findById(data.id());
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            return ResponseEntity.ok(optionalProduct);
        }
        throw new EntityNotFoundException();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public  ResponseEntity deleteProduct(@PathVariable String id){
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setActive(false);
            return  ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException();
    }


}
