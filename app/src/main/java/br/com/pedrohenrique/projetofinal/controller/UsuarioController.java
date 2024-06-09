package br.com.pedrohenrique.projetofinal.controller;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.pedrohenrique.projetofinal.model.Usuario;

public class UsuarioController {

    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;
    private final Context context;

    public UsuarioController(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public String getUidUsuarioAtual() {
        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
        if (usuarioFirebase == null) return "";
        return usuarioFirebase.getUid();
    }

    public boolean isConvidado() {
        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
        return usuarioFirebase != null && usuarioFirebase.isAnonymous();
    }

    public Task<Boolean> loginConvidado() {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    taskCompletionSource.setResult(true);
                } else {
                    taskCompletionSource.setException(task.getException());
                }
            }
        });
        return taskCompletionSource.getTask();
    }

    public Task<Boolean> logar(String email, String senha) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    taskCompletionSource.setResult(true);
                } else {
                    Toast.makeText(context, "Email ou Senha Incorretos ou Inexistentes", Toast.LENGTH_SHORT).show();
                    taskCompletionSource.setException(task.getException());
                }
            }
        });
        return taskCompletionSource.getTask();
    }

    public Task<Boolean> cadastrar(String nome, String email, String senha, String telefone, String endereco) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
                    if (usuarioFirebase != null) {
                        String uid = usuarioFirebase.getUid();
                        Usuario usuario = new Usuario(uid, nome, usuarioFirebase.getEmail(), telefone, endereco);
                        DocumentReference referencia = db.collection("usuarios").document(uid);
                        referencia.set(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    taskCompletionSource.setResult(true);
                                } else {
                                    taskCompletionSource.setException(task.getException());
                                }
                            }
                        });
                    } else {
                        taskCompletionSource.setException(new Exception("FirebaseUser is null after sign up"));
                    }
                } else {
                    taskCompletionSource.setException(task.getException());
                }
            }
        });
        return taskCompletionSource.getTask();
    }
}