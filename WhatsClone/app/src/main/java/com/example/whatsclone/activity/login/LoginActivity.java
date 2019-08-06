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
import com.example.whatsclone.activity.conversas.ConversasActivity;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.obj.UsuarioObj;
import com.example.whatsclone.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private static ActivityLoginBinding mBinding;
    private UsuarioObj mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mAuth = ConfiguracaoFirebase.getAutenticacao();

        verificarUsuarioLogado();

        mUser = new UsuarioObj();

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
                mUser.getEmail(),
                mUser.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    irParaTelaConversas();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao efetuar login!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void obterUsuario() {
        mUser.setEmail(mBinding.edtEmail.getText().toString());
        mUser.setSenha(mBinding.edtSenha.getText().toString());
    }

    public void abrirCadastro(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void irParaTelaConversas() {
        Intent intent = new Intent(LoginActivity.this, ConversasActivity.class);
        startActivity(intent);
        finish();
    }
}
