package com.example.whatsclone.activity.conversas;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_conversas);

        mAuth = ConfiguracaoFirebase.getAutenticacao();

        mBinding.btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                voltar();
            }
        });
    }

    private void voltar() {
        Intent intent = new Intent(ConversasActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
