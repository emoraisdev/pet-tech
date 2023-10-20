package br.com.fiap.pettech.pettech.controller;

import br.com.fiap.pettech.pettech.entity.Produto;
import br.com.fiap.pettech.pettech.service.ProdutoService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;


    @GetMapping
    public ResponseEntity<Collection<Produto>> findAll(){

        var lista = service.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable UUID id){

        var produto = service.findById(id);
        return ResponseEntity.ok(produto);
    }


    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody Produto produto){

        produto = service.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable UUID id, @RequestBody Produto produto){

        produto = service.update(id, produto);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
