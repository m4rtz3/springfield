package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cidadao")
public class CidadaoController {

    private static final Logger logger = LoggerFactory.getLogger(CidadaoController.class);

    @Autowired
    private CidadaoRepository repo;

    // Listar todos os cidadãos
    // GET http://localhost:8080/cidadao/todos
    @GetMapping("/todos")
    public ResponseEntity<Iterable<CidadaoEntity>> obterTodosCidadaos() {
        logger.debug("Obtendo todos os cidadãos");
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    // Consultar um cidadão pelo ID
    // GET http://localhost:8080/cidadao/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CidadaoEntity> obterCidadao(@PathVariable Integer id) {
        logger.debug("Obtendo dados do cidadão com ID: " + id);
        return repo.findById(id)
                   .map(cidadao -> new ResponseEntity<>(cidadao, HttpStatus.OK))
                   .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Cadastrar um novo cidadão
    // POST http://localhost:8080/cidadao
    @PostMapping
    public ResponseEntity<String> criarCidadao(@RequestBody CidadaoEntity novoCidadao) {
        logger.debug("Cadastrando cidadão: " + novoCidadao.getNome());
        repo.save(novoCidadao);
        return new ResponseEntity<>("Cidadão criado: " + novoCidadao.getNome(), HttpStatus.CREATED);
    }

    // Atualizar os dados de um cidadão
    // PUT http://localhost:8080/cidadao/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CidadaoEntity> atualizarCidadao(@PathVariable Integer id, @RequestBody CidadaoEntity cidadao) {
        if (!repo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        cidadao.setId(id);
        repo.save(cidadao);
        logger.debug("Atualizando dados do cidadão: " + cidadao.getNome());
        return new ResponseEntity<>(cidadao, HttpStatus.OK);
    }
}