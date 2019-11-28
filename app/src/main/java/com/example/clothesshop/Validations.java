package com.example.clothesshop;

import android.app.Activity;

import com.basgeekball.awesomevalidation.AwesomeValidation;

public class Validations {

    String namePattern = "^[a-zA-Z -]+$";

    String emailPattern = "^[a-zA-Z]\\w+(.\\w+)*@\\w+(.[0-9a-zA-Z]+)*.[a-zA-Z]{2,4}$";

    String phoneRegex = "^[6-9][0-9]{9}$";

    String passwordRegex = "^[a-zA-Z0-9]{6,}$";

    String amountRegex ="^([0-9])(\\.)([0-9])$";


    public void checkEmail(AwesomeValidation awesomeValidation, int i, Activity activity){
        awesomeValidation.addValidation(activity,i,emailPattern, R.string.emailErr);
    }

    public void checkName(AwesomeValidation awesomeValidation, int i, Activity activity){
        awesomeValidation.addValidation(activity,i,namePattern,R.string.nameErr);
    }

    public void checkMobile(AwesomeValidation awesomeValidation, int i, Activity activity){
        awesomeValidation.addValidation(activity,i,phoneRegex,R.string.mobileErr);
    }

    public void checkPass(AwesomeValidation awesomeValidation, int i, Activity activity){
        awesomeValidation.addValidation(activity,i,passwordRegex,R.string.passErr);
    }

    public void checkLoginPass(AwesomeValidation awesomeValidation, int i, Activity activity){
        awesomeValidation.addValidation(activity,i,passwordRegex,R.string.loginPassErr);
    }

    public void checkAmount(AwesomeValidation awesomeValidation, int i, Activity activity){
        awesomeValidation.addValidation(activity,i,amountRegex,R.string.amountErr);
    }



  /*  public boolean checkValid() {

        if (edtRegisterName.getText().toString().equalsIgnoreCase("")) {
            edtRegisterName.setError("Please enter full name");
            return false;
        } else if (!edtRegisterName.getText().toString().matches(namePattern)) {
            edtRegisterName.setError("Name contains only Uppercase and Lowercase Letters");
            return false;
        } else if (edtRegisterEmail.getText().toString().equalsIgnoreCase("")) {
            edtRegisterEmail.setError("Please enter email id");
            return false;
        } else if (!edtRegisterEmail.getText().toString().matches(emailPattern)) {
            edtRegisterEmail.setError("Please enter valid email id");
            return false;
        } else if (edtRegisterMobile.getText().toString().equalsIgnoreCase("")) {
// toastClass.makeToast(this, "Please enter mobile number");
            edtRegisterMobile.setError("Please enter mobile number");
            return false;
        }else if (!edtRegisterMobile.getText().toString().matches(phoneRegex)) {
            edtRegisterMobile.setError("Please enter valid phone number");
            return false;
        }
        else if (mBinder.edtregisterpassword.getText().toString().equalsIgnoreCase("")) {
            edtRegisterPassword.setError("Please enter password");
            return false;
        } else if (!edtRegisterPassword.getText().toString().matches(passwordRegex)) {
            edtRegisterPassword.setError(" Password must be at least 8 characters");
            return false;
        }
        return true;
    }
*/

}
