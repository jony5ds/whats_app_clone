package com.example.whatsclone.activity.cadastro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.whatsclone.R;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.databinding.ActivityCadastroUsuarioBinding;
import com.example.whatsclone.obj.UsuarioObj;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroUsuarioActivity extends AppCompatActivity {

  private static ActivityCadastroUsuarioBinding mBinding;
    UsuarioObj mUsuario;
    FirebaseAuth autentica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mBinding = DataBindingUtil.setContentView(this,R.layout.activity_cadastro_usuario);
       mUsuario = new UsuarioObj();

       mBinding.btnCadastrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               obterDados();
               cadastrarUsuario();

           }
       });

    }

    private void obterDados() {
       mUsuario.setNome(mBinding.edtCadastroNome.getText().toString());
       mUsuario.setEmail(mBinding.edtCadastroEmail.getText().toString());
       mUsuario.setSenha(mBinding.edtCadastroSenha.getText().toString());
    }

    private void cadastrarUsuario() {
        autentica = ConfiguracaoFirebase.getAutenticacao();
        autentica.createUserWithEmailAndPassword(
                mUsuario.getEmail(),mUsuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    Toast.makeText(getApplicationContext(),getString(R.string.sucesso_cadastro),Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),getString(R.string.erro_envio),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
