package com.example.whatsclone.activity.conversas.telaConversas;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.whatsclone.R;
import com.example.whatsclone.config.ConfiguracaoFirebase;
import com.example.whatsclone.databinding.TelaConversaBinding;
import com.example.whatsclone.helper.Base64Custom;
import com.example.whatsclone.helper.Preferencias;
import com.example.whatsclone.obj.ContatoObj;
import com.example.whatsclone.obj.MensagemObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

public class ConversaActivity extends AppCompatActivity {

    private TelaConversaBinding mBinding;
    private ContatoObj mDestinatario;
    private DatabaseReference mReference;
    private String mRemetente;
    private ArrayList<String> mMensagens;
    private ArrayAdapter mAdapter;
    private ValueEventListener eventListenerMensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.tela_conversa);

        mDestinatario = obterDadosUsuario();



        configurarToolbar();

        //montar Lista e Adapter
        mMensagens = new ArrayList<>();
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mMensagens);
        mBinding.listaConversa.setAdapter(mAdapter);

        //Populando a lista de mensagens

        mReference = ConfiguracaoFirebase.getFirebase()
                .child("mensagens")
                .child(mRemetente)
                .child(mDestinatario.getId());

        //Criando listner para mensagens

        eventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mMensagens.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    MensagemObj mensagem = dados.getValue(MensagemObj.class);
                    mMensagens.add(mensagem.getMensagem());
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };

        mReference.addValueEventListener(eventListenerMensagem);

    }

    private void configurarToolbar() {
        mBinding.toolbarConversa.toolbarPrincipal.setTitle("Usuario");
        setSupportActionBar(mBinding.toolbarConversa.toolbarPrincipal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mBinding.toolbarConversa.toolbarPrincipal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConversaActivity.this.finish();
            }

        });

        mBinding.toolbarConversa.toolbarPrincipal.setTitle(mDestinatario.getNome());
    }

    private ContatoObj obterDadosUsuario() {
        ContatoObj contato = new ContatoObj();
        Preferencias preferencias = new Preferencias(this);
        mRemetente = preferencias.getIdentificador();
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            contato.setNome(extra.getString("nome"));
            contato.setEmail(extra.getString("email"));
            contato.setId(Base64Custom.codificarBase64(extra.getString("email")));
        }

        return contato;
    }

    public void enviarMensagem(View view) {
        String textoDigitado = mBinding.mensagem.getText().toString();
        if (!textoDigitado.isEmpty()) {
            MensagemObj mensagemObj = new MensagemObj();
            mensagemObj.setIdUsuario(mDestinatario.getId());
            mensagemObj.setMensagem(textoDigitado);

            salvarMensagem(mRemetente, mDestinatario.getId(), mensagemObj);
            mBinding.mensagem.setText("");

        }
    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, MensagemObj mensagemObj) {
        try {
            mReference = ConfiguracaoFirebase.getFirebase();
            mReference.child("mensagens").child(idRemetente).child(idDestinatario)
                    .push().setValue(mensagemObj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mReference.removeEventListener(eventListenerMensagem);
    }
}
