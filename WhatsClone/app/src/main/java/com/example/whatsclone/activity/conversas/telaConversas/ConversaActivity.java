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
import com.example.whatsclone.obj.ConversaObj;
import com.example.whatsclone.obj.MensagemObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    private TelaConversaBinding mBinding;
    private ContatoObj mDestinatario;
    private DatabaseReference mReference;
    private String mRemetente;
    private String mNomeRemetente;
    private ArrayList<MensagemObj> mMensagens;
    private ArrayAdapter<MensagemObj> mAdapter;
    private ValueEventListener eventListenerMensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.tela_conversa);

        mDestinatario = obterDadosUsuario();

        Preferencias preferencias = new Preferencias(this);
        mRemetente = preferencias.getIdentificador();
        mNomeRemetente = preferencias.getNomeUsuarioLogado();

        configurarToolbar();

        //montar Lista e Adapter
        mMensagens = new ArrayList<>();

        mAdapter = new MensagemAdapter(this, mMensagens);
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
                    mMensagens.add(mensagem);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };

        mReference.addValueEventListener(eventListenerMensagem);

    }

    private ConversaObj obterConversaDestinario(MensagemObj mensagem) {

        ConversaObj conversaObj = new ConversaObj();
        conversaObj.setIdUsuario(mDestinatario.getId());
        conversaObj.setMensagem(mensagem.getMensagem());
        conversaObj.setNome(mDestinatario.getNome());

        return conversaObj;
    }

    private ConversaObj obterConversaRemetente(MensagemObj mensagem) {

        ConversaObj conversaObj = new ConversaObj();
        conversaObj.setIdUsuario(mRemetente);
        conversaObj.setNome(mNomeRemetente);
        conversaObj.setMensagem(mensagem.getMensagem());
        return conversaObj;
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
            mensagemObj.setIdUsuario(mRemetente);
            mensagemObj.setMensagem(textoDigitado);

            salvarMensagem(mRemetente, mDestinatario.getId(), mensagemObj);
            salvarMensagem(mDestinatario.getId(), mRemetente, mensagemObj);
            mBinding.mensagem.setText("");

            ConversaObj conversaDestinatario = obterConversaDestinario(mensagemObj);

            salvarConversa(mRemetente,mDestinatario.getId(),conversaDestinatario);

            ConversaObj conversaRemetente = obterConversaRemetente(mensagemObj);

            salvarConversa(mDestinatario.getId(),mRemetente,conversaRemetente);


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

    private boolean salvarConversa(String idRemetente,
                                   String idDestinatario,
                                   ConversaObj conversaObj) {
        try {
            mReference = ConfiguracaoFirebase.getFirebase().child("conversas");
            mReference.child(idRemetente)
                    .child(idDestinatario)
                    .setValue(conversaObj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
