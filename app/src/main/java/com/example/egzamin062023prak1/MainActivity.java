package com.example.egzamin062023prak1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button checkBtn, confirmBtn;
    RadioButton pocztowkaRB, listRB, paczkaRB;
    ImageView imageView;
    TextView priceTV;
    EditText streetET, cityCodeET, cityET;
    char[] cyfry = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        imageView = findViewById(R.id.imageView);
        pocztowkaRB = findViewById(R.id.pocztowka_radioButton);
        listRB = findViewById(R.id.list_radioButton);
        paczkaRB = findViewById(R.id.paczka_radioButton);
        priceTV = findViewById(R.id.price_textView);
        streetET = findViewById(R.id.street_editText);
        cityCodeET = findViewById(R.id.cityCode_editText);
        cityET = findViewById(R.id.city_editText);


        checkBtn = findViewById(R.id.check_button);
        checkBtn.setOnClickListener(view -> {
            if(pocztowkaRB.isChecked()) {
                imageView.setImageResource(R.drawable.pocztowka);
                priceTV.setText("Cena: 1 zł");
            } else if (listRB.isChecked()) {
                imageView.setImageResource(R.drawable.list);
                priceTV.setText("Cena: 1,5 zł");
            } else if (paczkaRB.isChecked()) {
                imageView.setImageResource(R.drawable.paczka);
                priceTV.setText("Cena: 10 zł");
            }
        });

        confirmBtn = findViewById(R.id.confirm_button);
        confirmBtn.setOnClickListener(view -> {
            String cityCode = cityCodeET.getText().toString().trim();

            boolean noNumber = false;
            for (int i = 0; i < cityCode.length(); i++) {
                boolean pasuje = false;
                for (int j = 0; j < cyfry.length; j++) {
                    if(cityCode.charAt(i) == cyfry[j]) {
                        pasuje = true;
                        break;
                    }
                }
                if(!pasuje) {
                    noNumber = true;
                }
            }

            if (cityCode.length() != 5) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Walidacja")
                        .setMessage("Nieprawidłowa liczba cyfr w kodzie pocztowym")
                        .setPositiveButton("OK", null)
                        .show();
            } else if (noNumber) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Walidacja")
                        .setMessage("Kod pocztowy powinien się składać z samych cyfr")
                        .setPositiveButton("OK", null)
                        .show();
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Walidacja")
                        .setMessage("Dane przesyłki zostały wprowadzone")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
    }
}