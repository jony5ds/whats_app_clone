package com.example.whatsclone.obj;

import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class UsuarioObj {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public UsuarioObj(){

    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvar()
    {
        DatabaseReference reference = ConfiguracaoFirebase.getFirebase();
        reference.child("Usuarios").child(getId()).setValue(this);
    }
}
