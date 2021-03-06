package com.bharatarmy.appenum;

import android.graphics.Color;

import com.bharatarmy.R;

public enum PasswordStrength {
    // we use some color in green tint =>
    //more secure is the password, more darker is the color associated
    WEAK(R.string.weak, Color.parseColor("#FF0000")),
    MEDIUM(R.string.medium, Color.parseColor("#FFD700")),
    STRONG(R.string.strong, Color.parseColor("#1b5732")),
    VERY_STRONG(R.string.very_strong, Color.parseColor("#1b5732"));

    public int msg;
    public int color;
    private static int MIN_LENGTH = 4;
    private static int MAX_LENGTH = 10;

    public static String uppercase, lowercase, digitcase, specialcharcase, minimumcharcase;


    PasswordStrength(int msg, int color) {
        this.msg = msg;
        this.color = color;
    }

    public static PasswordStrength calculate(String password) {
        int score = 0;
        // boolean indicating if password has an upper case
        boolean upper = false;
        // boolean indicating if password has a lower case
        boolean lower = false;
        // boolean indicating if password has at least one digit
        boolean digit = false;
        // boolean indicating if password has a leat one special char
        boolean specialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (!specialChar && !Character.isLetterOrDigit(c)) {
                score++;
                specialChar = true;
            } else {
                if (!digit && Character.isDigit(c)) {
                    score++;
                    digit = true;
                } else {
                    if (!upper || !lower) {
                        if (Character.isUpperCase(c)) {
                            upper = true;
                        } else {
                            lower = true;
                        }

                        if (upper && lower) {
                            score++;
                        }
                    }
                }
            }
        }

        int length = password.length();

        if (length > MAX_LENGTH) {
            score++;
        } else if (length < MIN_LENGTH) {
            minimumcharcase="no";
            score = 0;
        }else if (length > MIN_LENGTH){
            minimumcharcase="yes";
        }

        if (upper == true) {
            uppercase = "yes";
        } else {
            uppercase = "no";
        }

        if (lower == true) {
            lowercase = "yes";
        } else {
            lowercase = "no";
        }
        if (digit == true) {
            digitcase = "yes";
        } else {
            digitcase = "no";
        }

        if (specialChar == true) {
            specialcharcase = "yes";
        } else {
            specialcharcase = "no";
        }




        // return enum following the score
        switch (score) {
            case 0:
                return WEAK;
            case 1:
                return MEDIUM;
            case 2:
                return STRONG;
            case 3:
                return VERY_STRONG;
            default:
        }

        return VERY_STRONG;
    }
}
