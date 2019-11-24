package pl.koszela.app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
}
