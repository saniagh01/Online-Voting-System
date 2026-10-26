package com.example.voting.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> chain = new ArrayList<>();

    public Blockchain() {
        chain.add(new Block(0, "genesis", "0"));
    }

    public synchronized Block addBlock(String data) {
        Block last = chain.get(chain.size() - 1);
        Block b = new Block(chain.size(), data, last.getHash());
        chain.add(b);
        return b;
    }

    public List<Block> getChain() {
        return List.copyOf(chain);
    }

    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block prev = chain.get(i - 1);
            if (!current.getPreviousHash().equals(prev.getHash())) return false;
            if (!current.getHash().equals(current.calculateHash())) return false;
        }
        return true;
    }
}
