package com.example.whatsclone.activity.conversas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.whatsclone.R;
import com.example.whatsclone.activity.conversas.adapter.Tab_adapter;
import com.example.whatsclone.activity.login.LoginActivity;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.databinding.ActivityConversasBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ConversasActivity extends AppCompatActivity {

    private ActivityConversasBinding mBinding;
    private FirebaseAuth mAuth;

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
        EditText editText = new EditText(this);
        alertDialog.setView(editText);

        // configurar botões
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
