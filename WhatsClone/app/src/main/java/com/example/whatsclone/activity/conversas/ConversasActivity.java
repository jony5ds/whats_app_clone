package com.example.whatsclone.activity.conversas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatsclone.R;
import com.example.whatsclone.activity.conversas.adapter.Tab_adapter;
import com.example.whatsclone.activity.login.LoginActivity;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.databinding.ActivityConversasBinding;
import com.example.whatsclone.helper.Base64Custom;
import com.example.whatsclone.helper.Preferencias;
import com.example.whatsclone.obj.ContatoObj;
import com.example.whatsclone.obj.UsuarioObj;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ConversasActivity extends AppCompatActivity {

    private ActivityConversasBinding mBinding;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDao;
    private String mIdentificadorContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_conversas);
        mAuth = ConfiguracaoFirebase.getAutenticacao();
        configurarToolbarViewPager();

    }

    private void configurarToolbarViewPager() {

        mBinding.contentToolbar.toolbarPrincipal.setTitle(getString(R.string.whats_app));
        setSupportActionBar(mBinding.contentToolbar.toolbarPrincipal);

        //Configurar Tabs
        mBinding.tabs.setDistributeEvenly(true);
        mBinding.tabs.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.ascent));

        // Configurar Adapter
        Tab_adapter tabAdapter = new Tab_adapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(tabAdapter);
        mBinding.tabs.setViewPager(mBinding.viewPager);

    }

    private void voltar() {
        Intent intent = new Intent(ConversasActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_add:
                abrirCadastroContato();
                return true;
            case R.id.item_pesquisa:
                return true;
            case R.id.item_configuracao:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroContato() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //configurações da dialog
        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("E-mail do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(this);
        alertDialog.setView(editText);

        // configurar botões
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailDigitado = editText.getText().toString();

                //Verifica se o email foi digitado
                if (emailDigitado.isEmpty()) {
                    Toast.makeText(ConversasActivity.this, "Digite um email",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //verifica se o usuário ja está cadastrado no app
                    mIdentificadorContato = Base64Custom.codificarBase64(emailDigitado);
                    mFirebaseDao = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(mIdentificadorContato);

                    mFirebaseDao.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {

                                UsuarioObj usuarioContato =  dataSnapshot.getValue(UsuarioObj.class);

                                Preferencias preferencias = new Preferencias(ConversasActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();
                                mFirebaseDao = ConfiguracaoFirebase.getFirebase();
                                mFirebaseDao = mFirebaseDao.child("contatos")
                                        .child(identificadorUsuarioLogado).child(mIdentificadorContato);

                                ContatoObj contato = new ContatoObj();
                                contato.setId(mIdentificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());

                                mFirebaseDao.setValue(contato);

                            } else {
                                Toast.makeText(ConversasActivity.this,
                                        "Usuário não cadastrado", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }

    private void deslogarUsuario() {
        mAuth.signOut();
        voltar();
    }
}
