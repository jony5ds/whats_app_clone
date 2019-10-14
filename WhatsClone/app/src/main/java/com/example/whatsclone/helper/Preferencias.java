package com.example.whatsclone.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {
    private Context mContext;
    private SharedPreferences mPreferences;
    private final String NOME_ARQUIVO = "whatsapp_preferencias";
    private final int MODO = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private final String CHAVE_NOME = "nomeUsuarioLogado";

    @SuppressLint("CommitPrefEdits")
    public Preferencias(Context context) {
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(NOME_ARQUIVO, MODO);
        editor = mPreferences.edit();
    }

    public void salvarDados(String identificadorUsario, String nome) {
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsario);
        editor.putString(CHAVE_NOME, nome);
        editor.commit();
    }

    public String getIdentificador() {
        return mPreferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNomeUsuarioLogado() { return mPreferences.getString(CHAVE_NOME, null); }


}
