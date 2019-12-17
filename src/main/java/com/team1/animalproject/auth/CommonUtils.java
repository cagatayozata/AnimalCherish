package com.team1.animalproject.auth;

import java.util.Optional;

public final class CommonUtils {

    private CommonUtils() {
    }


    public static Optional<String> sayisalStringDegeriArttir(String sayi, int artisMiktari) {
        try {
            return Optional.ofNullable(String.valueOf(Integer.parseInt(sayi) + artisMiktari));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}

