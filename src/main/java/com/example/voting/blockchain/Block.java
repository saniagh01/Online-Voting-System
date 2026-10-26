package com.example.voting.blockchain;

import java.time.Instant;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class Block {
    private int index;
    private long timestamp;
    private String data;
    private String previousHash;
    private String hash;

    public Block(int index, String data, String previousHash) {
        this.index = index;
        this.timestamp = Instant.now().toEpochMilli();
        this.data = data;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        try {
            String input = index + Long.toString(timestamp) + data + previousHash;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getIndex() { return index; }
    public long getTimestamp() { return timestamp; }
    public String getData() { return data; }
    public String getPreviousHash() { return previousHash; }
    public String getHash() { return hash; }
}
