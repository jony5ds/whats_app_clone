package com.example.whatsclone.activity.cadastro;

import android.content.Intent;
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
import com.example.whatsclone.activity.conversas.ConversasActivity;
import com.example.whatsclone.activity.login.LoginActivity;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.databinding.ActivityCadastroUsuarioBinding;
import com.example.whatsclone.helper.Base64Custom;
import com.example.whatsclone.helper.Preferencias;
import com.example.whatsclone.obj.UsuarioObj;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

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
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.sucesso_cadastro), Toast.LENGTH_SHORT).show();
                    String novoUsuario = Base64Custom.codificarBase64(mUsuario.getEmail());
                    mUsuario.setId(novoUsuario);
                    mUsuario.salvar();

                    Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                    preferencias.salvarDados(novoUsuario);

                    irParaTelaConversas();
                }
                else {
                    String erro_exception;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro_exception = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e ) {
                        erro_exception = "Email digitado é inválido!";
                    }catch (FirebaseAuthUserCollisionException e)
                    {
                        erro_exception = "Usuário já cadastrado !";
                    }
                    catch (Exception e) {
                        erro_exception = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), erro_exception, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void irParaTelaConversas()
    {
        Intent intent = new Intent(this, ConversasActivity.class);
        startActivity(intent);
        finish();

    }

    private void salvarUsuarioLogado() {
        Preferencias preferencias = new Preferencias(this);
        String identificadorUsuarioLogado = Base64Custom.codificarBase64(mUsuario.getEmail());
        preferencias.salvarDados(identificadorUsuarioLogado);
    }
}
