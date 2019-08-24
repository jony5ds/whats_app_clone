package com.example.whatsclone.activity.conversas.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.whatsclone.activity.conversas.telaPrincipal.ContatoFragment;
import com.example.whatsclone.activity.conversas.telaPrincipal.ConversasFragment;

public class Tab_adapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"CONVERSAS","CONTATOS"};

    public Tab_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment = new ConversasFragment();
                break;
            case 1:
                fragment = new ContatoFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
