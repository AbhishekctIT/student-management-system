package com.platformcommons.student_management_system.studentms;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[64]; // 512 bits
        random.nextBytes(keyBytes);
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        System.out.println("=====================================");
        System.out.println("JWT Secret Key (Base64):");
        System.out.println(base64Key);
        System.out.println("=====================================");
        System.out.println();
        System.out.println("Add this to your application.yml:");
        System.out.println("jwt:");
        System.out.println("  secret: " + base64Key);
        System.out.println("  expiration: 86400000");
    }
}
