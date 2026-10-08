package com.magdezis.config;

import org.mindrot.jbcrypt.BCrypt;

public final class SecurityConfig {

    private static final int COST = 12;

    private SecurityConfig() {
    }

    public static String encriptar(String textoPlano) {
        return BCrypt.hashpw(textoPlano, BCrypt.gensalt(COST));
    }

    public static boolean verificar(String textoPlano, String hash) {
        return BCrypt.checkpw(textoPlano, hash);
    }
}
