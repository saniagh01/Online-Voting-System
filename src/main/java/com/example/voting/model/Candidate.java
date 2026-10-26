package com.example.voting.model;

import jakarta.persistence.*;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    private String party;

    private Long votes = 0L;

    public Candidate() {}

    public Candidate(String name, String party) {
        this.name = name;
        this.party = party;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }
    public Long getVotes() { return votes; }
    public void setVotes(Long votes) { this.votes = votes; }
    public void incrementVotes() { this.votes = this.votes + 1; }
}
