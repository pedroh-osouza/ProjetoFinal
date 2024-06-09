package br.com.pedrohenrique.projetofinal.controller;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import br.com.pedrohenrique.projetofinal.model.Usuario;

public class UsuarioController {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public UsuarioController()
    {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    public Usuario getUsuarioAtual() {
        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
        if (usuarioFirebase != null) {
            mDatabase.child("usuarios").child(usuarioFirebase.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        System.out.println("tome");
                    } else {

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("tome");
                }
            });
        }
        return null;
    }

    public void logar(String email, String senha)
    {
        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                }
            }
        });

    }

    public boolean cadastrar(String nome, String email, String senha, String telefone, String endereco)
    {
        this.cadastraFirebaseAuth(email, senha);
        return this.cadastraUsuarioRealtimeDatabase(nome, telefone, endereco);
    }

    private void cadastraFirebaseAuth(String email, String senha)
    {
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                }
            }
        });
    }

    private boolean cadastraUsuarioRealtimeDatabase(String nome, String telefone, String endereco)
    {
        FirebaseUser usuarioFirebase = mAuth.getCurrentUser();
        if(usuarioFirebase == null) return false;
        String uid = usuarioFirebase.getUid();
        Usuario usuario = new Usuario(uid, nome, usuarioFirebase.getEmail(), telefone, endereco);
        mDatabase.child("usuarios").child(uid).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    System.out.println("cadastrou");
                }
            }
        });
        return true;
    }
}
