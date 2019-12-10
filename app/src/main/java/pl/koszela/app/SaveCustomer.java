package pl.koszela.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SaveCustomer extends AppCompatActivity {

    EditText name;
    EditText phone;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    Spinner dropdown;
    EditText number;
//    private TextView mPoints;
    private TextView text1;
    private TextView text2;
    private TextView id;

    private TextView mDetailID;

    private FirebaseAuth mAuth;

    private Button mSignOutButton;
    private String result;
    private String str_id;
    private String str_points;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_customer);

        name = (EditText) findViewById(R.id.et_new_customer_name);
        phone = (EditText) findViewById(R.id.et_new_customer_phone);
        number = (EditText) findViewById(R.id.editNumber);
        mSignOutButton = (Button) findViewById(R.id.logout);
        checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);
        mDetailID = findViewById(R.id.userId);
        text1 = findViewById(R.id.textView7);
        text2 = findViewById(R.id.textView5);
        save = (Button) findViewById(R.id.btnSaveCustomer);

        mAuth = FirebaseAuth.getInstance();

        result = getIntent().getStringExtra("message_key");
        if (result != null) {
            int login = result.indexOf("points");
            str_id = result.substring(0, login);
            String textID = mDetailID.getText().toString() + " " + str_id;
            mDetailID.setText(textID);
        } else {
            Toast.makeText(this, "Nie mogę wstawić id. Nic nie ma", Toast.LENGTH_LONG).show();
        }
        dropdown = findViewById(R.id.comboBox);
        String[] items = new String[]{"dach pierwszy", "dach drugi", "dach trzeci", "dach czwarty"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    String res = "";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                getIntent().removeExtra("message_key");
                Intent intent = new Intent(SaveCustomer.this, Points.class);
                intent.putExtra("message_key", result);
                startActivity(intent);
                return true;
            case R.id.item2:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void saveCustomer(View view) {
        String str_name = name.getText().toString();
        String str_phone = phone.getText().toString();
        String type = "save customer";
        String selectItemFromComboBox = dropdown.getSelectedItem().toString();
        String checked1 = Boolean.toString(checkBox1.isChecked());
        String checked2 = Boolean.toString(checkBox2.isChecked());
        String checked3 = Boolean.toString(checkBox3.isChecked());
        String checked4 = Boolean.toString(checkBox4.isChecked());
        String str_number = number.getText().toString();
        BackgroundWorker backgroundWorker = new BackgroundWorker(this, new MyCallback() {
            @Override
            public void onResult(String result) {
                res = result;
            }
        });
        backgroundWorker.execute(type, str_name, str_phone, selectItemFromComboBox, checked1, checked2, checked3, checked4, str_number, str_id);
    }

    public void addListenerOnButton() {

        checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);

        checkBox1.setOnClickListener(new View.OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {

            }
        });

    }


    public void OnLogout(View view) {
        mAuth.signOut();
        getIntent().removeExtra("message_key");
        startActivity(new Intent(SaveCustomer.this, MainActivity.class));
    }
}
