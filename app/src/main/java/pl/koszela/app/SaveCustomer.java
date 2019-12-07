package pl.koszela.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SaveCustomer extends AppCompatActivity {

    EditText name;
    EditText phone;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    Spinner dropdown;
    EditText number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_customer);

        name = (EditText) findViewById(R.id.et_new_customer_name);
        phone = (EditText) findViewById(R.id.et_new_customer_phone);
        number = (EditText) findViewById(R.id.editNumber);
        addListenerOnButton();

        //get the spinner from the xml.
        dropdown = findViewById(R.id.comboBox);
//create a list of items for the spinner.
        String[] items = new String[]{"dach pierwszy", "dach drugi", "dach trzeci", "dach czwarty"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
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

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_name, str_phone, selectItemFromComboBox, checked1, checked2, checked3, checked4, str_number);
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

                StringBuffer result = new StringBuffer();
                result.append("Opcja 1 : ").append(checkBox1.isChecked());
                result.append("\nOpcja 2 : ").append(checkBox2.isChecked());
                result.append("\nOpcja 3 : ").append(checkBox3.isChecked());
                result.append("\nOpcja 4 : ").append(checkBox4.isChecked());

                Toast.makeText(SaveCustomer.this, result.toString(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}
