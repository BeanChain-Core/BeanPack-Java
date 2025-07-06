package com.beanpack.Utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Utility for validating BeanChain addresses.
 *
 * Accepts both standard addresses and a whitelist of special system addrs.
 */
public final class AddressUtils {

    // Standard Beanchain address format: optional "0x" + 40 hex chars
    private static final Pattern STANDARD_PATTERN =
        Pattern.compile("^BEANX:(?:0x)?[0-9a-fA-F]{40}$");

    // Whitelist of known system addresses that skirt the normal format check
    private static final Set<String> SPECIAL_ADDRESSES;
    static {
        Set<String> m = new HashSet<>();
        m.add("BEANX:0xBURNTOKEN");
        SPECIAL_ADDRESSES = Collections.unmodifiableSet(m);
    }

    private AddressUtils() { /* static-only */ }

    /**
     * True if this is either a normal BeanChain address or an allowed special addr.
     */
    public static boolean isValidAddress(String address) {
        if (address == null) return false;
        if (SPECIAL_ADDRESSES.contains(address)) return true;
        return STANDARD_PATTERN.matcher(address).matches();
    }

    /**
     * Validate a sender address (strict format only).
     * @throws IllegalArgumentException if null or malformed.
     */
    public static void validateSender(String address) {
        if (address == null || !STANDARD_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Invalid sender address: " + address);
        }
    }

    /**
     * Validate a recipient address (allows the burn-address exception).
     * @throws IllegalArgumentException if null or not in the allowed set/pattern.
     */
    public static void validateRecipient(String address) {
        if (!isValidAddress(address)) {
            throw new IllegalArgumentException("Invalid recipient address: " + address);
        }
    }
    
    

    /** Simple manual test. */
    public static void main(String[] args) {
        String[] samples = {
            "BEANX:0x0a1b2c3d4e5f6a7b8c9d01e2f3a4b5c6d7e8f9a0",
            "BEANX:0A1B2C3D4E5F6A7B8C9D01E2F3A4B5C6D7E8F9A0",
            "BEANX:0xfe508e34380ec977efc42e7eca282e55e393bfa3",
            "BEANX:0xBURNTOKEN",
            "BEANX:0x012345",
            null
        };

        for (String addr : samples) {
            System.out.printf("'%s' → %s%n",
                addr,
                isValidAddress(addr) ? "VALID" : "INVALID"
            );
        }
    }
}