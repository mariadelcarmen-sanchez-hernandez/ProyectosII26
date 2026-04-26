package com.generaction.backend.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class DniValidationService {

    private static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
    private static final Pattern DNI_REGEX = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$", Pattern.CASE_INSENSITIVE);

    public DniValidationResult validar(String dniRaw) {
        if (dniRaw == null || dniRaw.isBlank()) {
            return new DniValidationResult(false, null, null, null, null, "DNI requerido", "DNI_REQUERIDO");
        }

        String dni = dniRaw.replaceAll("\\s+", "").toUpperCase();
        if (!DNI_REGEX.matcher(dni).matches()) {
            return new DniValidationResult(false, dni, null, null, null, "Formato invalido", "FORMATO_INVALIDO");
        }

        int numero = Integer.parseInt(dni.substring(0, 8));
        String letra = dni.substring(8);
        String letraCalculada = String.valueOf(LETRAS_DNI.charAt(numero % 23));
        boolean valido = letra.equals(letraCalculada);

        return new DniValidationResult(
                valido,
                dni,
                numero,
                letra,
                letraCalculada,
                valido ? null : "Letra de control incorrecta",
                valido ? null : "LETRA_INVALIDA"
        );
    }

    public record DniValidationResult(
            boolean valido,
            String dni,
            Integer numero,
            String letra,
            String letraCalculada,
            String error,
            String codigo
    ) {
    }
}