package com.example.whatsclone.activity.conversas.telaConversas;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.whatsclone.R;
import com.example.whatsclone.databinding.ActivityConversasBinding;
import com.example.whatsclone.databinding.TelaConversaBinding;

public class ConversaActivity extends AppCompatActivity {

    private TelaConversaBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.tela_conversa);

        mBinding.toolbarConversa.toolbarPrincipal.setTitle("Usuario");
        setSupportActionBar(mBinding.toolbarConversa.toolbarPrincipal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mBinding.toolbarConversa.toolbarPrincipal.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ConversaActivity.this.finish();
            }

        });


    }
}
