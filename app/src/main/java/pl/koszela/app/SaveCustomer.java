package pl.koszela.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SaveCustomer extends AppCompatActivity {

    EditText name;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_customer);

        name = (EditText) findViewById(R.id.et_new_customer_name);
        phone = (EditText) findViewById(R.id.et_new_customer_phone);

    }

    public void saveCustomer(View view) {
        String str_name = name.getText().toString();
        String str_phone = phone.getText().toString();
        String type = "save customer";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_name, str_phone);
    }
}
