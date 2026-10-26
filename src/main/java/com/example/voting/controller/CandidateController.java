package com.example.voting.controller;

import com.example.voting.model.Candidate;
import com.example.voting.repository.CandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final CandidateRepository candidateRepo;

    public CandidateController(CandidateRepository candidateRepo) {
        this.candidateRepo = candidateRepo;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Candidate candidate) {
        if (candidate.getName() == null || candidate.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Candidate name required");
        }
        Candidate saved = candidateRepo.save(new Candidate(candidate.getName(), candidate.getParty()));
        return ResponseEntity.created(URI.create("/api/candidates/" + saved.getId())).body(saved);
    }

    @GetMapping
    public List<Candidate> list() {
        return candidateRepo.findAll();
    }
}
