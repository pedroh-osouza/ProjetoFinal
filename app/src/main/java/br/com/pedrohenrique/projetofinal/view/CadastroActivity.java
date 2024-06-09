package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.controller.UsuarioController;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CadastroActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etEmail;
    private EditText etAddress;
    private EditText etPassword;
    private EditText etPhone;
    private Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String endereco = etAddress.getText().toString().trim();
                String senha = etPassword.getText().toString().trim();
                String telefone = etPhone.getText().toString().trim();

                // Verifica se os campos não estão vazios
                if (nome.isEmpty() || email.isEmpty() || endereco.isEmpty() || senha.isEmpty() || telefone.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    UsuarioController usuarioController = new UsuarioController(CadastroActivity.this);
                    usuarioController.cadastrar(nome, email, senha, telefone, endereco).addOnCompleteListener(new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}