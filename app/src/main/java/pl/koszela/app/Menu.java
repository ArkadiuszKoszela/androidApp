package pl.koszela.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends AppCompatActivity {

    private String result;
    private FirebaseAuth mAuth;
    String id;
    private String str_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();
        result = getIntent().getStringExtra("message_key");

        if (result != null && !result.equals("")) {
            int login = result.indexOf("points");
            id = result.substring(0, login);

            int login1 = result.indexOf('s');
            int login2 = result.indexOf("login");
            str_points = result.substring(login1 + 1, login2);
        }
        if (getIntent().getStringExtra("str_points") != null && getIntent().getStringExtra("id") != null) {
            str_points = getIntent().getStringExtra("str_points");
            id = getIntent().getStringExtra("id");
        }
    }

    public void GotoUrl(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nowoczesnebudowanie.pl/"));
        startActivity(browserIntent);
    }

    public void GoToSaveCustomer(View view) {
        Intent intent = new Intent(Menu.this, SaveCustomer.class);
        intent.putExtra("message_key", result);
        if (getIntent().getStringExtra("str_points") != null) {
            str_points = getIntent().getStringExtra("str_points");
            intent.putExtra("str_points", str_points);
            intent.putExtra("id", id);
        }else{
            intent.putExtra("str_points", str_points);
            intent.putExtra("id", id);
        }
        startActivity(intent);
    }

    public void GotoPoints(View view) {
        Intent intent = new Intent(Menu.this, Points.class);
        intent.putExtra("message_key", result);
        if (getIntent().getStringExtra("str_points") != null) {
            str_points = getIntent().getStringExtra("str_points");
            intent.putExtra("str_points", str_points);
            intent.putExtra("id", id);

        }else{
            intent.putExtra("str_points", str_points);
            intent.putExtra("id", id);
        }
        startActivity(intent);
    }

    public void OnLogout1(View view) {
        mAuth.signOut();
        getIntent().removeExtra("message_key");
        startActivity(new Intent(Menu.this, MainActivity.class));
    }
}
