package com.example.whatsclone.activity.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsclone.R;
import com.example.whatsclone.activity.cadastro.CadastroUsuarioActivity;
import com.example.whatsclone.activity.conversas.telaPrincipal.TelaPrincipalActivity;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.helper.Base64Custom;
import com.example.whatsclone.helper.Preferencias;
import com.example.whatsclone.obj.UsuarioObj;
import com.example.whatsclone.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static ActivityLoginBinding mBinding;
    private UsuarioObj mUsuario;
    private FirebaseAuth mAuth;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private String identificadorUsuarioLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAuth = ConfiguracaoFirebase.getAutenticacao();

        verificarUsuarioLogado();

        mUsuario = new UsuarioObj();

        mBinding.btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Primeiro passo inputar o valores digitados pelo usuário no objeto
                obterUsuario();

                // Segundo passo validar o que foi digitado pelo usuário no banco
                validarLogin();
            }
        });
    }

    private void verificarUsuarioLogado() {
        if (mAuth.getCurrentUser() != null)
            irParaTelaConversas();
    }

    private void validarLogin() {

        mAuth.signInWithEmailAndPassword(
                mUsuario.getEmail(),
                mUsuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //salva email do usuário ao logar
                    salvarUsuarioLogado();
                    irParaTelaConversas();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao efetuar login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void salvarUsuarioLogado() {

        identificadorUsuarioLogado = Base64Custom.codificarBase64(mUsuario.getEmail());

        firebase = ConfiguracaoFirebase.getFirebase()
                .child("contatos").child(identificadorUsuarioLogado);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsuarioObj usuarioRecuperado = dataSnapshot.getValue(UsuarioObj.class);
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarDados(identificadorUsuarioLogado,usuarioRecuperado.getNome());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        firebase.addListenerForSingleValueEvent(valueEventListener);

    }

    private void obterUsuario() {
        mUsuario.setEmail(mBinding.edtEmail.getText().toString());
        mUsuario.setSenha(mBinding.edtSenha.getText().toString());
    }

    public void abrirCadastro(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void irParaTelaConversas() {
        Intent intent = new Intent(LoginActivity.this, TelaPrincipalActivity.class);
        startActivity(intent);
        finish();
    }
}
