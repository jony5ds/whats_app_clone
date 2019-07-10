package com.example.whatsclone.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.whatsclone.R;
import com.example.whatsclone.activity.cadastro.CadastroUsuarioActivity;
import com.example.whatsclone.obj.UsuarioObj;
import com.example.whatsclone.databinding.ActivityLoginBinding;
import com.example.whatsclone.helper.Permissoes;
import com.example.whatsclone.helper.Preferencias;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private static ActivityLoginBinding mBinding;
    private UsuarioObj user;
    private String[] permissoes = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }

    public void abrirCadastro(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }
}
