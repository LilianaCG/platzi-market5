package com.platzi.market.web.controller;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.service.PurchaseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    @ApiOperation("Get all purchases")
    @ApiResponse(code = 200, message = "Listado Correctamente")
    public ResponseEntity<List<Purchase>> getAll(){
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    @ApiOperation("Search a client with an ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Encontrado Correctamente"),
            @ApiResponse(code = 484, message = "Cliente no Encontrado"),
    })
    public ResponseEntity <List<Purchase>> getByClient(@PathVariable("clientId") String clientId){
        return purchaseService.getByClient(clientId).map
                (purchases -> new ResponseEntity<>(purchases, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/save")
    @ApiOperation("Save a purchase")
    @ApiResponse(code = 200, message = "Guardado Correctamente")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase){
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }
}
