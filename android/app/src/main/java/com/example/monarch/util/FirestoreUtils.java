package com.example.monarch.util;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreUtils {
    public static void modifyFireStore(String FeatureName, String VisitTime) {
        Map<String, Object> rec = new HashMap<>();
        rec.put("activityName", FeatureName);
        rec.put("visitTime", VisitTime);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Add a new document with a generated ID
        db.collection("ActivityLog5").add(rec).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("firestore", "one visit Log success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("firestore", "Error adding document", e);
            }
        });
    }

    public static void clickLog(String FeatureName, String VisitTime) {
        Map<String, Object> rec = new HashMap<>();
        rec.put("buttonName", FeatureName);
        rec.put("clickTime", VisitTime);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

// Add a new document with a generated ID
        db.collection("ClickLog5").add(rec).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("firestore", "one visit Log success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("firestore", "Error adding document", e);
            }
        });
    }


}
