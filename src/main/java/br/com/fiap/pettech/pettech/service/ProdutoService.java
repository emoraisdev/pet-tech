package br.com.fiap.pettech.pettech.service;

import br.com.fiap.pettech.pettech.dto.ProdutoDTO;
import br.com.fiap.pettech.pettech.repository.ProdutoRepository;
import br.com.fiap.pettech.pettech.entity.Produto;
import br.com.fiap.pettech.pettech.controller.exception.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    public Collection<ProdutoDTO> findAll() {
        return repo
                .findAll()
                .stream()
                .map(this::toProdutoDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO findById(UUID id) {
        return toProdutoDTO(repo
                .findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Entity not Found")));
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        return toProdutoDTO(repo.save(toProduto(produtoDTO)));
    }

    public ProdutoDTO update(UUID id, ProdutoDTO produtoDTO) {

        try {
            Produto produtodb = repo.getReferenceById(id);

            produtodb.setNome(produtoDTO.nome());
            produtodb.setDescricao(produtoDTO.descricao());
            produtodb.setUrlDaImagem(produtoDTO.urlDaImagem());
            produtodb.setPreco(produtoDTO.preco());

            return toProdutoDTO(repo.save(produtodb));
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Produto não Encontrado");
        }

    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }

    private ProdutoDTO toProdutoDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getUrlDaImagem());
    }

    private Produto toProduto(ProdutoDTO produtoDTO) {

        return new Produto(
                produtoDTO.id(),
                produtoDTO.nome(),
                produtoDTO.descricao(),
                produtoDTO.preco(),
                produtoDTO.urlDaImagem());
    }
}
