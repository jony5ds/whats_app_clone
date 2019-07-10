package com.example.whatsclone.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissoes {
    //Verifica as permissões que o usário tem e solicita as permissões
    public static boolean validaPermissoes(int requestCode,Activity activity, String[] permissoes) {
        if(Build.VERSION.SDK_INT >= 23)
        {
            /**
             *Percorre as permissões verificando uma a uma
             * se já tem a permissão liberada
             */
            List<String> listaPermissoes = new ArrayList<String>();
            for(String permissao : permissoes)
            {
                Boolean valida = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;
                if(!valida) listaPermissoes.add(permissao);
            }

            /* Caso a lista esteja vazia, não é necessário solicitar permissão*/
            if(listaPermissoes.isEmpty()) return true;

            String[] permissoesValidadas = new String[listaPermissoes.size()];
            listaPermissoes.toArray(permissoesValidadas);

            //Solicitar a permissão
            ActivityCompat.requestPermissions(activity,permissoesValidadas,requestCode);
        }
        return true;
    }
}
