package com.beanpack.Block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import com.beanpack.Utils.hex;
import com.beanpack.crypto.SHA256TransactionSigner;
import com.beanpack.crypto.TransactionVerifier;
import com.beanpack.crypto.WalletGenerator;
import com.beanpack.logger.PackLoggerManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Represents a BeanChain block containing metadata, transactions, and a signature from the validator.
 * <p>
 * Each block includes a height, previous block hash, a list of transaction hashes,
 * a Merkle root, and a signature for validation. This class provides serialization, hashing,
 * Merkle root calculation, and signature verification.
 */
public class Block {
    private int height;
    private String previousHash;
    private String hash;
    @JsonProperty("merkleRoot")
    private String merkleroot;
    private List<String> transactions;
    private long timeStamp;
    private String validatorPubKey;
    private String signature;
    @JsonProperty("header")
    private BlockHeader header;
    


    
    public int getHeight() {return height;}
    public String getMerkleRoot() {return merkleroot;}
    public long getTimeStamp() {return timeStamp;}
    public String getPreviousHash() {return previousHash;}
    public String getHash() {return hash;}
    public List<String> getTransactions() {return transactions;}
    public String getValidatorPubKey() {return validatorPubKey;}
    public String getSignature() {return signature;}
    public BlockHeader getHeader() {return header;}

    public void setHeight(int height) {this.height = height;}
    public void setMerkleRoot(String merkleroot) {this.merkleroot = merkleroot;}
    public void setTimeStamp(long timeStamp) {this.timeStamp = timeStamp;}
    public void setPreviousHash(String previousHash) {this.previousHash = previousHash;}
    public void setHash(String hash) {this.hash = hash;}
    public void setTransactions(List<String> transactions) {this.transactions = transactions;}
    public void setValidatorPubKey(String validatorPubKey) {this.validatorPubKey = validatorPubKey;}
    public void setSignature(String signature) {this.signature = signature;}
    public void setHeader(BlockHeader header) { this.header = header; }

    /**
     * Default no-args constructor for deserialization.
     */
    public Block() {

    }
    

    /**
     * Constructs a new block with a given height, previous hash, transaction list, and validator private key.
     * Automatically calculates the Merkle root, block hash, and signs the block.
     *
     * @param height         The block height
     * @param previousHash   The previous block hash
     * @param transactions   List of transaction hashes
     * @param validatorPrivKey The validator's private key in hex form
     * @throws Exception if signing fails
     */
    public Block(int height, String previousHash, List<String> transactions, String validatorPrivKey) throws Exception {
        this.height = height;
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis();
        this.transactions = transactions;
        this.merkleroot = calculateMerkleRoot();
        this.hash = calculateBlockHash();
        sign(WalletGenerator.restorePrivateKey(validatorPrivKey));
    }

    /** @return The Merkle root for this block. */
    public String calculateMerkleRoot() {
        return calculateMerkleRoot(this.transactions);
    }


    /** 
     * Calculates the SHA-256 block hash using height, previous hash, and Merkle root.
     * @return the block hash
     */
    public String calculateBlockHash(){
        try {
            String safeMerkleRoot = (merkleroot == null) ? "" : merkleroot;
            String data = Integer.toString(height) + previousHash + safeMerkleRoot;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for(byte b: hash){
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Serializes this block to a JSON string.
     * @return JSON representation of the block
     */
    public String createJSON() {
        String jsonString = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules(); // <-- FIX: support nested classes
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false); // Optional safety
            jsonString = objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            PackLoggerManager.PackLoggerError("EXCEPTION: " + e.getMessage());
        }
        return jsonString;
    }

    /**
     * Deserializes a block from a JSON string.
     * @param json the JSON input
     * @return the reconstructed Block object or null if parsing fails
     */
    public static Block fromJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(json, Block.class);
        } catch (Exception e) {
            PackLoggerManager.PackLoggerError("EXCEPTION: " + e.getMessage());
            return null;
        }
    }

    /**
     * Signs the block hash using the validator's private key and stores the public key and signature.
     * @param privateKey the validator's private key
     * @throws Exception if signing fails
     */
    public void sign(PrivateKey privateKey) throws Exception{
        validatorPubKey = WalletGenerator.generatePublicKey(privateKey);
        
        byte[] transactionHash = hex.hexToBytes(this.hash);
        signature = SHA256TransactionSigner.signSHA256Transaction(privateKey, transactionHash);
    }

    /**
     * Verifies the block signature using the validator's public key.
     * @return true if valid, false otherwise
     * @throws Exception if verification fails
     */
    public boolean signatureValid() throws Exception{
        if(TransactionVerifier.verifySHA256Transaction(this.validatorPubKey, hex.hexToBytes(hash), this.signature)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates the block's integrity by checking hash, Merkle root, previous hash, and signature.
     * @param expectedPrevHash the expected previous hash
     * @return true if valid, false otherwise
     * @throws Exception if validation or signature check fails
     */
    public boolean validateBlock(String expectedPrevHash) throws Exception {
    
        boolean merkleValid = this.getMerkleRoot().equals(this.calculateMerkleRoot());
        boolean hashValid = this.getHash().equals(this.calculateBlockHash());
        boolean signatureValid = this.signatureValid();
        boolean previousHashValid = this.getPreviousHash().equals(expectedPrevHash);
    
        if (merkleValid && hashValid && signatureValid && previousHashValid) {
            return true;
        } else {
            System.err.println("Block failed validation:");
            if (!merkleValid) System.err.println(" - Merkle root mismatch");
            if (!hashValid) System.err.println(" - Hash mismatch: Expected: " + this.getHash() + " Calculated: " + this.calculateBlockHash());
            if (!signatureValid) System.err.println(" - Invalid signature");
            if (!previousHashValid) {
                System.err.println(" - Invalid Previous Hash");
                System.err.println("   ➤ Expected: " + expectedPrevHash);
                System.err.println("   ➤ Found:    " + this.getPreviousHash());
            }
            return false;
        }
    }

    /**
     * Calculates a Merkle root from a given list of transaction hashes.
     * @param txHashes list of transaction hashes
     * @return the Merkle root
     */
    public static String calculateMerkleRoot(List<String> txHashes) {
        if (txHashes == null || txHashes.isEmpty()) return "";

        List<String> currentLevel = new ArrayList<>(txHashes);

        while (currentLevel.size() > 1) {
            List<String> nextLevel = new ArrayList<>();

            for (int i = 0; i < currentLevel.size(); i += 2) {
                String left = currentLevel.get(i);
                String right = (i + 1 < currentLevel.size()) ? currentLevel.get(i + 1) : left;
                nextLevel.add(sha256(left + right));
            }

            currentLevel = nextLevel;
        }

        return currentLevel.get(0);
    }

    /**
     * Computes the SHA-256 hash of a given input string.
     *
     * @param input the input string to hash
     * @return the resulting hash as a hexadecimal string
     * @throws RuntimeException if the hashing process fails
     */
    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("SHA-256 calculation failed", e);
        }
    }

    /**
     * Verifies that the stored Merkle root matches the recalculated one.
     * @return true if they match, false otherwise
     */
    public boolean verifyMerkleRoot() {
        String recalculatedRoot = calculateMerkleRoot();
        return this.merkleroot.equals(recalculatedRoot);
    }


    /**
     * Initializes the block header with validator identity, height, hash, and gas fee.
     * @param gasFee the total gas fee rewarded in this block
     */
    public void initHeader(long gasFee){
        BlockHeader h = new BlockHeader();
        h.setValidator(validatorPubKey);
        h.setHeight(height);
        h.setPreviousHash(previousHash);
        h.setGasFeeReward(gasFee);
        this.header = h;
    }
    
}
