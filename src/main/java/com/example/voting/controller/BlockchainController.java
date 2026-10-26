package com.example.voting.controller;

import com.example.voting.blockchain.Block;
import com.example.voting.blockchain.Blockchain;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blockchain")
public class BlockchainController {

    private final Blockchain chain = new Blockchain();

    @PostMapping("/add")
    public Block add(@RequestBody String data) {
        return chain.addBlock(data);
    }

    @GetMapping("/chain")
    public List<Block> chain() {
        return chain.getChain();
    }

    @GetMapping("/valid")
    public boolean valid() {
        return chain.isValid();
    }
}
