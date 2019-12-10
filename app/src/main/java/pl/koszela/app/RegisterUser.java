package pl.koszela.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterUser extends AppCompatActivity {

    EditText name;
    EditText surname;
    EditText email;
    EditText phone;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.et_new_name);
        surname = (EditText) findViewById(R.id.et_new_surname);
        email = (EditText) findViewById(R.id.et_new_email);
        phone = (EditText) findViewById(R.id.et_new_phone);
        username = (EditText) findViewById(R.id.et_new_username);
        password = (EditText) findViewById(R.id.et_new_password);

    }

    public void registerUser(View view) {
        if (!validation()) {
            return;
        }
        String str_name = name.getText().toString();
        String str_surname = surname.getText().toString();
        String str_email = email.getText().toString();
        String str_phone = phone.getText().toString();
        String str_username = username.getText().toString();
        String str_password = password.getText().toString();
        String type = "register user";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_name, str_surname, str_email, str_phone, str_username, str_password);
    }


    public boolean validation() {
        String textName = name.getText().toString();
        String textSurname = surname.getText().toString();
        String textEmail = email.getText().toString();
        String textPhone = phone.getText().toString();
        String textUsername = username.getText().toString();
        String textPassword = password.getText().toString();
        if (validateName(name, textName) &&
                validateName(surname, textSurname) &&
                validateName(email, textEmail) &&
                validateName(phone, textPhone) &&
                validateName(username, textUsername) &&
                validateName(password, textPassword) &&
                validatePhone(phone, textPhone)) {
            Toast.makeText(this, "Wprowadzone dane są poprawne. Zapisuję użytkownika", Toast.LENGTH_LONG).show();
            return true;
        }
        Toast.makeText(this, "Nie można zapisać użytkownika. Wprowadzone dane są nieprawidłowe", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean validatePhone(EditText phone, String textPassword) {
        if (validatePrefix(textPassword)) {
            phone.setError("Telefon musi składać się z formatu (+48xxxxxxxxx) ");
            return false;
        }
        return true;
    }

    private boolean validatePrefix(String textPassword) {
        String verifyText = textPassword.substring(0, 3);
        if (!verifyText.equals("+48")) {
            return true;
        } else return textPassword.substring(3).length() != 9;
    }

    private boolean validateName(EditText name, String text) {
        if (text.isEmpty()) {
            name.setError("Wartośc nie może być pusta");
            return false;
        }
        return true;
    }
}
