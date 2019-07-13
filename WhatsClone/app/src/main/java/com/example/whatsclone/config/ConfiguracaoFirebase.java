package com.example.whatsclone.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFireBase;

    public static DatabaseReference getFirebase() {

        if(referenciaFireBase == null)
         referenciaFireBase  = FirebaseDatabase.getInstance().getReference();

        return referenciaFireBase;
    }
}
