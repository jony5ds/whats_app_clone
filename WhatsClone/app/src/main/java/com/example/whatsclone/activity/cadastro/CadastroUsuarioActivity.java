package com.example.whatsclone.activity.cadastro;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.whatsclone.R;
import com.example.whatsclone.databinding.ActivityCadastroUsuarioBinding;

public class CadastroUsuarioActivity extends AppCompatActivity {

  private static ActivityCadastroUsuarioBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mBinding = DataBindingUtil.setContentView(this,R.layout.activity_cadastro_usuario);


    }

}
