package com.example.voting.controller;

import com.example.voting.blockchain.Block;
import com.example.voting.blockchain.Blockchain;
import com.example.voting.model.Candidate;
import com.example.voting.model.Vote;
import com.example.voting.model.Voter;
import com.example.voting.repository.CandidateRepository;
import com.example.voting.repository.VoteRepository;
import com.example.voting.repository.VoterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteRepository voteRepo;
    private final VoterRepository voterRepo;
    private final CandidateRepository candidateRepo;
    private final Blockchain chain;
    private final ObjectMapper mapper = new ObjectMapper();

    public static class CastRequest {
        public Long voterId;
        public Long candidateId;
    }

    public VoteController(VoteRepository voteRepo, VoterRepository voterRepo, CandidateRepository candidateRepo) {
        this.voteRepo = voteRepo;
        this.voterRepo = voterRepo;
        this.candidateRepo = candidateRepo;
        this.chain = new Blockchain();
    }

    @PostMapping("/cast")
    public ResponseEntity<?> cast(@RequestBody CastRequest req) {
        if (req.voterId == null || req.candidateId == null) {
            return ResponseEntity.badRequest().body("voterId and candidateId required");
        }

        Voter voter = voterRepo.findById(req.voterId).orElse(null);
        if (voter == null) return ResponseEntity.badRequest().body("Voter not found");
        if (voter.isHasVoted()) return ResponseEntity.badRequest().body("Voter has already voted");

        Candidate candidate = candidateRepo.findById(req.candidateId).orElse(null);
        if (candidate == null) return ResponseEntity.badRequest().body("Candidate not found");

        Vote vote = new Vote(voter, candidate);
        voteRepo.save(vote);

        voter.setHasVoted(true);
        voterRepo.save(voter);

        candidate.incrementVotes();
        candidateRepo.save(candidate);

        try {
            String data = mapper.writeValueAsString(Map.of("voterId", voter.getId(), "candidateId", candidate.getId()));
            Block blk = chain.addBlock(data);
        } catch (Exception e) {
            // ignore
        }

        return ResponseEntity.ok(Map.of("message", "Vote cast"));
    }

    @GetMapping("/results")
    public List<Candidate> results() {
        List<Candidate> all = candidateRepo.findAll();
        all.sort(Comparator.comparingLong(Candidate::getVotes).reversed());
        return all;
    }
}
