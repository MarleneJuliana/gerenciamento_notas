package com.example.gerenciadornotasapp;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edNome;
    private EditText edEmail;
    private EditText edIdade;
    private EditText edDisciplina;
    private EditText ed1Nota;
    private EditText ed2Nota;
    private TextView tvResultado;
    private TextView tvErro;
    private Button btLimpar;
    private Button btEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNome = findViewById(R.id.edNome);
        edEmail = findViewById(R.id.edEmail);
        edIdade = findViewById(R.id.edIdade);
        edDisciplina = findViewById(R.id.edDisciplina);
        ed1Nota = findViewById(R.id.ed1Nota);
        ed2Nota = findViewById(R.id.ed2Nota);
        btEnviar = findViewById(R.id.btEnviar);
        btLimpar = findViewById(R.id.btLimpar);
        tvResultado = findViewById(R.id.tvResultado);
        tvErro = findViewById(R.id.tvErro);

        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparFormulario();
            }
        });
    }

    private void cadastrar() {
        String nome = edNome.getText().toString();
        String email = edEmail.getText().toString();
        String idade = edIdade.getText().toString();
        String disciplina = edDisciplina.getText().toString();
        String nota1 = ed1Nota.getText().toString();
        String nota2 = ed2Nota.getText().toString();

        // Limpar mensagens de erro anteriores
        tvErro.setVisibility(View.GONE);
        tvErro.setText("");

        StringBuilder erros = new StringBuilder();

        // Validação
        if (nome.isEmpty()) {
            erros.append("O Nome é obrigatório(ex:Maria).\n");
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            erros.append("O Email é inválido(ex:@gmail.com).\n");
        }
        if (idade.isEmpty()) {
            erros.append("A Idade é obrigatória.\n");
        } else {
            try {
                int idadeP = Integer.parseInt(idade);
                if (idadeP <= 0) {
                    erros.append("Idade deve ser um número positivo.\n");
                }
            } catch (NumberFormatException e) {
                erros.append("Idade deve ser um número válido.\n");
            }
        }
        if (disciplina.isEmpty()) {
            erros.append("Disciplina é obrigatória(ex:História).\n");
        }


        if (nota1.isEmpty()) {
            erros.append("Nota 1° Bimestre é obrigatória.\n");
        } else {
            try {
                double nota1Bim = Double.parseDouble(nota1);
                if (nota1Bim < 0 || nota1Bim > 10) {
                    erros.append("Nota 1° Bimestre deve estar entre 0 e 10.\n");
                }
            } catch (NumberFormatException e) {
                erros.append("Nota 1° Bimestre deve ser um número válido(ex: 5.4 ou 5).\n");
            }
        }

            if (nota2.isEmpty()) {
                erros.append("Nota 2° Bimestre é obrigatória.\n");
            } else {
                try {
                    double nota2Bim = Double.parseDouble(nota2);
                    if (nota2Bim < 0 || nota2Bim > 10) {
                        erros.append("Nota 2° Bimestre deve estar entre 0 e 10.\n");
                    }
                } catch (NumberFormatException e) {
                    erros.append("Nota 2° Bimestre deve ser um número válido(ex: 5.4 ou 5).\n");
                }
            }

            if (erros.length() > 0) {
                tvErro.setText(erros.toString());
                tvErro.setVisibility(View.VISIBLE);
                return;
            }

            double media = calcularNota();

            String resultado = "Nome: " + nome
                    + "\nEmail: " + email
                    + "\nIdade: " + idade
                    + "\nDisciplina: " + disciplina
                    + "\nNota 1° Bimestre e 2° Bimestre : 1°Bim " + nota1 + " e 2°Bim" + nota2
                    + "\nMédia: " + media;

            String mensagemAprovacao = (media >= 6) ? "Aprovado" : "Reprovado";
            resultado += "\nSituação: " + mensagemAprovacao;

            tvResultado.setText(resultado);

    }

    private void limparFormulario() {
        edNome.setText("");
        edEmail.setText("");
        edIdade.setText("");
        edDisciplina.setText("");
        ed1Nota.setText("");
        ed2Nota.setText("");

        tvErro.setVisibility(View.GONE);

        Log.d("MainActivity", "Formulário limpo, mas resultado intacto.");

    }

    private double calcularNota(){
        String nota1 = ed1Nota.getText().toString();
        String nota2 = ed2Nota.getText().toString();
        double nota1Bim = Double.parseDouble(nota1);
        double nota2Bim = Double.parseDouble(nota2);
        return  (nota1Bim + nota2Bim) /2;
    }
}