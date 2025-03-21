package com.andreizubkov.taco_cloud.web.api;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.andreizubkov.taco_cloud.data.TacoRepository;
import com.andreizubkov.taco_cloud.tacos.Taco;

@RestController
@RequestMapping(path="/api/tacos", produces="application/json", consumes="application/json")
@CrossOrigin(origins="https://tacocloud:8443")
public class TacoController {
    
    private TacoRepository tacoRepo;

    public TacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(params="recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }

    @GetMapping("/{tacoId}")
    public ResponseEntity<Taco> tacoById(@PathVariable("tacoId") Long tacoId) {
        Optional<Taco> optTaco = tacoRepo.findById(tacoId);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

    @PutMapping("/{tacoId}")
    public Taco putTaco(@PathVariable("tacoId") Long tacoId, @RequestBody Taco taco) {
        taco.setId(tacoId);
        return tacoRepo.save(taco);
    }

    @PatchMapping("/{tacoId}")
    public Taco patchTaco(@PathVariable("tacoId") Long tacoId, @RequestBody Taco patch) {
        Taco taco = tacoRepo.findById(tacoId).get();
        if (patch.getName() != null) {
            taco.setName(patch.getName());
        }
        if (patch.getCreatedAt() != null) {
            taco.setCreatedAt(patch.getCreatedAt());
        }
        if (patch.getIngredients() != null) {
            taco.setIngredients(patch.getIngredients());
        }
        return tacoRepo.save(taco);
    }

    @DeleteMapping("/{tacoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaco(@PathVariable("tacoId") Long tacoId) {
        try {
            tacoRepo.deleteById(tacoId);
        } catch (EmptyResultDataAccessException e) {}
    }
}
