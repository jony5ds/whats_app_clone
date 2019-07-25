package com.example.whatsclone.activity.conversas;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.whatsclone.R;
import com.example.whatsclone.databinding.ActivityConversasBinding;

public class ConversasActivity extends AppCompatActivity {

    private ActivityConversasBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_conversas);
    }
}
