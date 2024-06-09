package br.com.pedrohenrique.projetofinal.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import br.com.pedrohenrique.projetofinal.model.Suprimento;
import br.com.pedrohenrique.projetofinal.model.Usuario;

public class UsuarioController {

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private final Context context;

    public UsuarioController(Context context)
    {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    public String getUidUsuarioAtual() {
        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
        if(usuarioFirebase == null) return "";
        return usuarioFirebase.getUid();
    }

    public boolean isConvidado()
    {
        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
        return true;
    }

    public void loginConvidado()
    {
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }
    public Task<Boolean> logar(String email, String senha) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        try {
            mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        taskCompletionSource.setResult(true);
                    } else {
                        Toast.makeText(context, "Email ou Senha Incorretos ou Inexistentes", Toast.LENGTH_SHORT).show();
                        taskCompletionSource.setResult(false);
                    }
                }
            });
        } catch (Exception e) {
            taskCompletionSource.setResult(false);
        }

        return taskCompletionSource.getTask();
    }
    public void cadastrar(String nome, String email, String senha, String telefone, String endereco)
    {
        this.cadastraFirebaseAuth(email, senha);
        this.cadastraUsuarioRealtimeDatabase(nome, telefone, endereco);
    }

    private void cadastraFirebaseAuth(String email, String senha)
    {
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });
    }

    private void cadastraUsuarioRealtimeDatabase(String nome, String telefone, String endereco)
    {
        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
        if(usuarioFirebase == null) return;
        String uid = usuarioFirebase.getUid();
        Usuario usuario = new Usuario(uid, nome, usuarioFirebase.getEmail(), telefone, endereco);
        DocumentReference referencia = db.collection("usuarios").document();
        referencia.set(usuario);
    }
}
