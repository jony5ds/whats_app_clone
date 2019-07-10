package com.example.whatsclone.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {
    private Context mContext;
    private SharedPreferences mPreferences;
    private final String NOME_ARQUIVO = "whatsapp_preferencias";
    private final int MODO = 0;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Preferencias(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(NOME_ARQUIVO,MODO);
        editor = mPreferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome,String telefone,String token)
    {
        editor.putString(Const.CHAVE_NOME.name(),nome);
        editor.putString(Const.CHAVE_TELEFONE.name(),telefone);
        editor.putString(Const.CHAVE_TOLKEN.name(),token);
        editor.commit();
    }

    public  HashMap<String,String> getDadosUsuario()
    {
        HashMap<String,String> dados = new HashMap<>();

        dados.put(Const.CHAVE_NOME.name(), mPreferences.getString(Const.CHAVE_NOME.name(),null));
        dados.put(Const.CHAVE_TELEFONE.name(), mPreferences.getString(Const.CHAVE_TELEFONE.name(),null));
        dados.put(Const.CHAVE_TOLKEN.name(), mPreferences.getString(Const.CHAVE_TOLKEN.name(),null));

        return dados;
    }
}
