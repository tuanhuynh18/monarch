package com.example.usr_page_test.user_page;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireStoreUtil {
    public static void modifyFireStore(String FeatureName, String VisitTime) {
        Map<String, Object> rec = new HashMap<>();
        rec.put("activityName", FeatureName);
        rec.put("visitTime", VisitTime);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Add a new document with a generated ID
        db.collection("users")
                .add(rec)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("firestore", "Error adding document", e);
                    }
                });
    }
}
