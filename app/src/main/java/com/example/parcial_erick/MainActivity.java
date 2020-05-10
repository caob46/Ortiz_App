package com.example.parcial_erick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DBControlador controlador;

    EditText edCedula, edNombre, edSalario;
    Spinner spEstrato, spNivel;

    Button btGuardar, btCancelar;

    int estrato, nivelEducativo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edCedula = findViewById(R.id.edCedula);
        edNombre = findViewById(R.id.edNombre);
        edSalario = findViewById(R.id.edSalario);
        spEstrato = findViewById(R.id.spEstrato);
        spNivel = findViewById(R.id.spNivelEducativo);
        btGuardar = findViewById(R.id.btGuardar);
        btCancelar = findViewById(R.id.btCancelar);

        controlador = new DBControlador(getApplicationContext());


        String g [] = { "1","2","3","4","5","6"};
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,g);
        spEstrato.setAdapter(aa);
        spEstrato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estrato = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String gg  [] = { "Bachillerato","Pregrado","Maestria","Doctorado"};
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,gg);
        spNivel.setAdapter(a);

        spNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nivelEducativo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btGuardar.setOnClickListener(this);
        btCancelar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btGuardar:
                try {
                    int cedula = edCedula.getText().toString().isEmpty() ? 0 : Integer.parseInt(edCedula.getText().toString());
                    int salario = edSalario.getText().toString().isEmpty() ? 0 : Integer.parseInt(edSalario.getText().toString());
                    Persona persona = new Persona(cedula, edNombre.getText().toString(), estrato, salario, nivelEducativo);
                    long retorno = controlador.agregarRegistro(persona);
                    if (retorno != -1) {
                        Toast.makeText(v.getContext(), "registro guardado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "registro fallido", Toast.LENGTH_SHORT).show();
                    }
                    limpiarCampo();
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btCancelar:
                limpiarCampo();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_buscar_registro:
                Intent intent1 = new Intent(this, BuscarActivity.class);
                startActivity(intent1);
                return true;
            case R.id.action_listado:
                Intent intent = new Intent(this, ListadoActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void limpiarCampo() {
        edCedula.setText("");
        edNombre.setText("");
        edSalario.setText("");
    }
}
