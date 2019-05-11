package com.projetocomercial.controller;

import com.projetocomercial.model.Oportunidade;
import com.projetocomercial.repository.OportunidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

        Optional<Oportunidade> opoExistente = oportunidades.findByDescricaoAndNomeProspecto(opo.getDescricao(),opo.getNomeProspecto());

        if(opoExistente.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Já Existe uma oportunidade para este prospecto com a mesma descrição!!!");
        }
        return oportunidades.save(opo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){

        Oportunidade opo = oportunidades.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Deu zebra!!!"));
        oportunidades.delete(opo);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Oportunidade> atualizar(@PathVariable Long id,@Valid @RequestBody Oportunidade opo){

        Oportunidade oportunidade = oportunidades.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Deu Errado!!!"));

        oportunidade.setNomeProspecto(opo.getNomeProspecto());
        oportunidade.setDescricao(opo.getDescricao());
        oportunidade.setValor(opo.getValor());

        Oportunidade oportu = oportunidades.save(oportunidade);

        return ResponseEntity.ok(oportu);
    }
}
