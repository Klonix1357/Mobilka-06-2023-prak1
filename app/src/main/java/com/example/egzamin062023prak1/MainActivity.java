package com.example.egzamin062023prak1;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    String street, cityCode, city, type;
    double price;
    Button checkBtn, confirmBtn;
    RadioButton pocztowkaRB, listRB, paczkaRB;
    RadioGroup radioGroup;
    ImageView imageView;
    TextView priceTV;
    EditText streetET, cityCodeET, cityET;

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
        radioGroup = findViewById(R.id.radioGroup);
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
        confirmBtn.setOnClickListener(view -> validate());
    }

    private void validate() {
        street = streetET.getText().toString().trim();
        cityCode = cityCodeET.getText().toString().trim();
        city = cityET.getText().toString().trim();

        // rodzaj przesyłki
        int selectedType = radioGroup.getCheckedRadioButtonId();
        if(selectedType == pocztowkaRB.getId()) {
            type = pocztowkaRB.getText().toString();
            price = 1;
        } else if (selectedType == listRB.getId()) {
            type = listRB.getText().toString();
            price = 1.5;
        } else if (selectedType == paczkaRB.getId()) {
            type = paczkaRB.getText().toString();
            price = 10;
        } else {
            type = "Nie wybrano";
            price = 0;
        }

        // walidacja kodu pocztowego - usuwanie myślników
        if(cityCode.contains("-")) {
            StringBuilder newCityCode = new StringBuilder(cityCode);
            while (newCityCode.toString().contains("-")) {
                newCityCode.deleteCharAt(newCityCode.indexOf("-"));
            }
            cityCode = newCityCode.toString();
        }


        // walidacja kodu pocztowego - szukanie znaków innych niż cyfry
        char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean noNumber = false;
        for (int i = 0; i < cityCode.length(); i++) {
            boolean pasuje = false;
            for (int j = 0; j < numbers.length; j++) {
                if(cityCode.charAt(i) == numbers[j]) {
                    pasuje = true;
                    break;
                }
            }
            if(!pasuje) {
                noNumber = true;
            }
        }

        // wyświetlenie komunikatu
        if (cityCode.length() != 5) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Walidacja - Błąd")
                    .setMessage("Nieprawidłowa liczba cyfr w kodzie pocztowym")
                    .setPositiveButton("OK", null)
                    .show();

        } else if (noNumber) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Walidacja - Błąd")
                    .setMessage("Kod pocztowy powinien się składać z samych cyfr")
                    .setPositiveButton("OK", null)
                    .show();

        } else if (street.isBlank()) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Walidacja - Błąd")
                    .setMessage("Nie wpisano nazwy ulicy")
                    .setPositiveButton("OK", null)
                    .show();

        } else if (city.isBlank()) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Walidacja - Błąd")
                    .setMessage("Nie wpisano nazwy miasta")
                    .setPositiveButton("OK", null)
                    .show();

        } else {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Walidacja - Sukces")
                    .setMessage("Dane przesyłki zostały wprowadzone")
                    .setNeutralButton("OK", null)
                    .setPositiveButton("Zobacz", seeMoreListener)
                    .show();
        }
    }

    private final DialogInterface.OnClickListener seeMoreListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            StringBuilder cityCodeFormatted = new StringBuilder(cityCode);
            if(!cityCode.contains("-")){
                cityCodeFormatted.insert(2, '-');
            }

            String information = "Rodzaj przesyłki: " + type + "\n" +
                    "Cena: " + price + " zł\n\n" +
                    "Adres przesyłki:\n" +
                    "- ulica: " + street + "\n" +
                    "- kod pocztowy: " + cityCodeFormatted + "\n" +
                    "- miasto: " + city;

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Dane przesyłki - szczegóły")
                    .setMessage(information)
                    .setPositiveButton("OK", null)
                    .show();
        }
    };
}