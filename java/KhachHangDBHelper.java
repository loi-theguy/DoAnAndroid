package com.example.doan2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KhachHangDBHelper extends DatabaseHelper<KhachHang>{
    private ArrayList<KhachHang> khachHangs;

    public KhachHangDBHelper() {
        super();
        khachHangs=new ArrayList<>();
    }

    @Override
    public DatabaseReference getReference() {
        return firebaseDatabase.getReference("KhachHang");
    }

    @Override
    public void read(DatabaseStatus<KhachHang> status) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                khachHangs.clear();
                ArrayList<String> keys=new ArrayList<>();
                for(DataSnapshot node: snapshot.getChildren()){
                    keys.add(node.getKey());
                    try {
                        KhachHang kh=node.getValue(KhachHang.class);
                        khachHangs.add(kh);
                    } catch (Exception e){
                        Log.e("CAST_ERR", e.getMessage());
                    }
                }
                status.doWhenRead(khachHangs,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void insert(DatabaseStatus<KhachHang> status, KhachHang instance) {
        databaseReference.push().setValue(instance).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                status.doWhenInserted();
            }
        });
    }

    @Override
    public void update(DatabaseStatus<KhachHang> status, String key, KhachHang instance) {
        databaseReference.child(key).setValue(instance).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                status.doWhenUpdated();
            }
        });
    }

    @Override
    public void delete(DatabaseStatus<KhachHang> status, String key) {
        databaseReference.child(key).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                status.doWhenDeleted();
            }
        });
    }


//    public void read(final DatabaseStatus<KhachHang> status)
//    {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                khachHangs.clear();
//                ArrayList<String> keys=new ArrayList<>();
//                for(DataSnapshot node: snapshot.getChildren()){
//                    keys.add(node.getKey());
//                    try {
//                        KhachHang kh=node.getValue(KhachHang.class);
//                        khachHangs.add(kh);
//                    } catch (Exception e){
//                        System.out.println(e.getStackTrace());
//                    }
////                    KhachHang kh=node.getValue(KhachHang.class);
////                    khachHangs.add(kh);
//                }
//                status.doWhenRead(khachHangs,keys);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
