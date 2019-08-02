package com.example.whatsclone.activity.conversas;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.whatsclone.R;
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
        mBinding.contentToolbar.toolbarPrincipal.setTitle(getString(R.string.whats_app));
        setSupportActionBar(mBinding.contentToolbar.toolbarPrincipal);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogarUsuario() {
        mAuth.signOut();
        voltar();
    }
}
