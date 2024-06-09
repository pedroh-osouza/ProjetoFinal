package br.com.pedrohenrique.projetofinal.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.pedrohenrique.projetofinal.R;
import br.com.pedrohenrique.projetofinal.controller.UsuarioController;
import br.com.pedrohenrique.projetofinal.model.Usuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            UsuarioController usuarioController = new UsuarioController();
            usuarioController.cadastrar("Pedro", "pedroh.olives@gmail.com", "12345678", "659996204736", "Logo ali");
            Usuario usuario = usuarioController.getUsuarioAtual();
            System.out.println(usuario);
        } catch (Exception e) {
            System.out.println("tome");
        }
    }
}