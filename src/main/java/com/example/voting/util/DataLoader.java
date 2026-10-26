package com.example.voting.util;

import com.example.voting.model.Candidate;
import com.example.voting.model.Voter;
import com.example.voting.repository.CandidateRepository;
import com.example.voting.repository.VoterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CandidateRepository candidateRepo;
    private final VoterRepository voterRepo;

    public DataLoader(CandidateRepository candidateRepo, VoterRepository voterRepo) {
        this.candidateRepo = candidateRepo;
        this.voterRepo = voterRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (candidateRepo.count() == 0) {
            candidateRepo.save(new Candidate("Alice Johnson", "Party A"));
            candidateRepo.save(new Candidate("Bob Kumar", "Party B"));
        }

        if (voterRepo.count() == 0) {
            voterRepo.save(new Voter("sadaf@example.com", "Sadaf G."));
            voterRepo.save(new Voter("user2@example.com", "User Two"));
        }
    }
}
