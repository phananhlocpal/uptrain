package com.h3lc.android.uptrain.Helper;

import android.util.Patterns;
import android.widget.EditText;

public class ValidateHelper {
    public static boolean validateEmail(EditText email){
        String emailInput = email.getText().toString();
        if(!emailInput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            return true;
        }
        else{
            return false;
        }

    }
    public static boolean validatePhone(EditText phone){
        String regex = "^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$";
        String phoneInput = phone.getText().toString();
        if(!phoneInput.isEmpty() && phoneInput.matches(regex)){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean validateAge(EditText age){
        String regex = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
        String ageInput = age.getText().toString();
        if(!ageInput.isEmpty() && ageInput.matches(regex)){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean validateName(EditText name){
        String nameInput = name.getText().toString();
        if(!nameInput.isEmpty() && !nameInput.matches(".*\\d.*")){
            return true;
        }
        else {
            return false;
        }
    }
}
