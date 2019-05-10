package com.projetocomercial.controller;

import com.projetocomercial.model.Oportunidade;
import com.projetocomercial.repository.OportunidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

    @Autowired
    private OportunidadeRepository oportunidades;

    @GetMapping
    public List<Oportunidade> listar(){
        return oportunidades.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oportunidade> buscar(@PathVariable Long id){

        Optional<Oportunidade> oportunidade = oportunidades.findById(id);

        if (!oportunidade.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oportunidade.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Oportunidade adicionar(@Valid @RequestBody Oportunidade opo){
        return oportunidades.save(opo);
    }
}
