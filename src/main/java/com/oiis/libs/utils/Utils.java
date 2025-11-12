package com.oiis.libs.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * utils contains common utils static methods to common use
 *
 * @author Alencar
 * @version 1.0.0
 */
public class Utils {


    /**
     * check if text is not null and is not empty
     * @param text the text to check
     * @return if text is not null and is not empty
     */
    public static boolean hasText(String text) {
        return text != null && !text.isEmpty();
    }

    /**
     * remove all except last dot from string
     * @param s the string with dots
     * @return the string with only last dot
     */
    public static String onlyLastDot(String s) {
        // 1) remove todos os pontos que NÃO sejam o último
        String onlyLastDot = s.replaceAll("\\.(?![^.]*$)", "");
        // 2) se não houver NENHUM ponto, adiciona um no final
        return onlyLastDot.contains(".") ? onlyLastDot : onlyLastDot + ".";
    }

    /**
     * safe cast string to big decimal
     * @param input the string to cast
     * @return the big decimal or null
     */
    public static BigDecimal safeStringToBigDecimal(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("[Conversion Error] Input is null or empty.");
            return null;
        }

        // Substitui vírgulas por pontos
        String normalized = input.replace(",", ".");

        // Remove tudo que não for número, +, -, ou ponto
        normalized = normalized.replaceAll("[^\\d+\\-\\.]", "");

        // Verifica se tem mais de um ponto
        int dotCount = normalized.length() - normalized.replace(".", "").length();
        if (dotCount > 1) {
            System.out.println("[Conversion Error] More than one decimal point found: " + normalized);
            return null;
        }

        // Tenta criar o BigDecimal
        try {
            if (normalized.isEmpty()) {
                System.out.println("[Conversion Error] String became empty after cleaning: " + input);
                return null;
            }
            return new BigDecimal(normalized);
        } catch (NumberFormatException e) {
            System.out.println("[Conversion Error] Failed to parse BigDecimal from: " + normalized);
            return null;
        }
    }

    /**
     * coalescence between multiple values
     * @param values multiple values of an type
     * @return the first value with is not null
     * @param <T> the type of values
     */
    public static <T> T coalesce(T... values) {
        if (values != null) {
            for (T value : values) {
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * trim left not digits characters
     * @param input the string input
     * @return the string trimmed
     */
    public static String onlyDigitsLeftTrim(String input) {
        if (hasText(input)) {
            return input.replaceAll("\\D", "").replaceFirst("^0+(?!$)", "");
        }
        return input;
    }

    /**
     * equals ignore case safe checking null
     * @param a the first string
     * @param b the first string
     * @return if is equals ignore case
     */
    public static boolean equalsIgnoreCaseSafe(String a, String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * get all fields including ascendant inherited fields of a type
     * @param type the class to get all fields
     * @return the list of all fields
     */
    public static ArrayList<Field> getAllFields(Class<?> type) {
        ArrayList<Field> fields = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>(); // to avoid duplicates
        for (Class<?> c = type; c != null && c != Object.class; c = c.getSuperclass()) {
            Field[] currentFields = c.getDeclaredFields();
            if (c.equals(type)) {
                for (int i = 0; i < currentFields.length; i++) {
                    if (!fieldNames.contains(currentFields[i].getName())) {
                        fieldNames.add(currentFields[i].getName());
                        fields.add(currentFields[i]);
                    }
                }
            } else {
                for (int i = currentFields.length-1; i > -1; i--) {
                    if (!fieldNames.contains(currentFields[i].getName())) {
                        fieldNames.add(currentFields[i].getName());
                        fields.add(0,currentFields[i]);
                    }
                }
            }
        }
        return fields;
    }

}
