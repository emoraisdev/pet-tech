package br.com.fiap.pettech.pettech.service;

import br.com.fiap.pettech.pettech.data.ProdutoRepository;
import br.com.fiap.pettech.pettech.entity.Produto;
import br.com.fiap.pettech.pettech.exception.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    public Collection<Produto> findAll(){
        return repo.findAll();
    }

    public Produto findById(UUID id){
        return repo.findById(id).orElseThrow(() ->  new ControllerNotFoundException("Entity not Found"));
    }

    public Produto save(Produto produto){
        return repo.save(produto);
    }

    public Produto update(UUID id , Produto produto) {

        try {
            Produto produtodb = repo.getOne(id);

            produtodb.setNome(produto.getNome());
            produtodb.setDescricao(produto.getDescricao());
            produtodb.setUrlDaImagem(produto.getUrlDaImagem());
            produtodb.setPreco(produto.getPreco());

            return repo.save(produtodb);
        } catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Produto não Encontrado");
        }

    }

    public void delete(UUID id){
        repo.deleteById(id);
    }
}
