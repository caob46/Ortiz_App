package com.example.parcial_erick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BuscarActivity extends AppCompatActivity implements View.OnClickListener {

    DBControlador controlador;

    EditText edCedula;

    Button btBuscar, btRegresar;

    TextView tvCedula, tvNombre, tvEstrato, tvSalario, tvNivel;
    int cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        edCedula = findViewById(R.id.edCedula);
        btBuscar = findViewById(R.id.btBuscar);
        btRegresar = findViewById(R.id.btRegresar);
        tvCedula = findViewById(R.id.tvCedula);
        tvNombre = findViewById(R.id.tvNombre);
        tvEstrato = findViewById(R.id.tvEstrato);
        tvSalario = findViewById(R.id.tvSalario);
        tvNivel = findViewById(R.id.tvNivelEducativo);

        controlador = new DBControlador(getApplicationContext());


        btBuscar.setOnClickListener(this);
        btRegresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBuscar:
                try {
                    cedula = Integer.parseInt(edCedula.getText().toString());
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "numero muy grande", Toast.LENGTH_SHORT).show();
                }
                Persona persona = controlador.buscarPersona(cedula);
                if (persona != null) {
                    tvCedula.setText(String.valueOf(cedula));
                    tvNombre.setText(persona.getNombre());
                    tvEstrato.setText(String.valueOf(persona.getEstrato2()));
                    tvSalario.setText(String.valueOf(persona.getSalario()));
                    switch (persona.getNivel_educativo()) {
                        case 0:
                            tvNivel.setText("Bachillerato");
                            break;
                        case 1:
                            tvNivel.setText("Pregado");
                            break;
                        case 2:
                            tvNivel.setText("Maestro");
                            break;
                        case 3:
                            tvNivel.setText("Doctorado");
                            break;
                    }
                } else {
                    tvCedula.setText("invalido");
                    tvNombre.setText("invalido");
                    tvEstrato.setText("invalido");
                    tvSalario.setText("invalido");
                    tvNivel.setText("invalido");
                    Toast.makeText(getApplicationContext(), "no encontrado", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btRegresar:
                finish();
                break;
        }
    }
}
