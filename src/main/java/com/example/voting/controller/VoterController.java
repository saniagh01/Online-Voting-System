package com.example.voting.controller;

import com.example.voting.model.Voter;
import com.example.voting.repository.VoterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    private final VoterRepository voterRepo;

    public VoterController(VoterRepository voterRepo) {
        this.voterRepo = voterRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Voter voter) {
        if (voter.getEmail() == null || voter.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body("Email required");
        }
        if (voterRepo.findByEmail(voter.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        Voter saved = voterRepo.save(new Voter(voter.getEmail(), voter.getName()));
        return ResponseEntity.created(URI.create("/api/voters/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return voterRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
