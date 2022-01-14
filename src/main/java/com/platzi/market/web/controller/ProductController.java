package com.platzi.market.web.controller;

import com.platzi.market.domain.Product;
import com.platzi.market.domain.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @ApiOperation("Get all supermarket products")
    @ApiResponse(code = 200, message = "Listado Correctamente")
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @ApiOperation("Search a product with an ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encontrado Correctamente"),
            @ApiResponse(code = 484, message = "Producto no Encontrado"),
    })
    public ResponseEntity<Product> getProduct(@ApiParam(value = "El ID del producto", required = true, example = "7")
                                                  @PathVariable("productId") int productId){
        return productService.getProduct(productId).map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{categoryId}")
    @ApiOperation("Search a category with an ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encontrado Correctamente"),
            @ApiResponse(code = 484, message = "Categoria no Encontrada"),
    })
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("categoryId") int categoryId){
        return productService.getByCategory(categoryId).map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    @ApiOperation("Save a product")
    @ApiResponse(code = 200, message = "Guardado Correctamente")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("Delete a product an ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Eliminado Correctamente"),
            @ApiResponse(code = 484, message = "Producto no Encontrado"),
    })
    public ResponseEntity delete(@PathVariable("id") int productId){
        if (productService.delete(productId)) {
        return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        }
}
