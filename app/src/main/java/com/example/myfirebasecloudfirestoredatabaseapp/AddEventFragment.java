package com.example.myfirebasecloudfirestoredatabaseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;


public class AddEventFragment extends Fragment {

    private static final String TAG = "AddEventFragment";

    private FirebaseFirestore firestoreDB;
    private boolean isEdit;
    private Button button;
    private TextView headTxt;
    private String docId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_event,
                container, false);

        button = (Button) view.findViewById(R.id.add_event);
        headTxt = (TextView)view.findViewById(R.id.add_tv);

        firestoreDB = FirebaseFirestore.getInstance();
        if(getArguments() != null){
            docId = getArguments().getString("ID");
        }
        if (docId != null) {
            populateUpdateAd(); //kiem tra neu adId != null thi se la layout edit ad
        }




        button.setOnClickListener(new View.OnClickListener()        {
            @Override
            public void onClick(View v)
            {
                if(!isEdit){
                    addEvent();
                }else {
                    updateEvent();
                }

            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    public void addEvent(){
        Event event = createEventObj();
        addDocumentToCollection(event);
    }
    public void updateEvent(){
        Event event = createEventObj();
        updateDocumentToCollection(event);
    }
    private void displayAdForUpdate(Event event){
        ((TextView) getActivity().findViewById(R.id.event_name_a)).setText(event.getName());
        ((TextView) getActivity().findViewById(R.id.event_type_a)).setText(event.getType());
        ((TextView) getActivity().findViewById(R.id.event_place_a)).setText(event.getPlace());
        ((TextView) getActivity().findViewById(R.id.event_start_time_a)).setText(event.getStartTime());
        ((TextView) getActivity().findViewById(R.id.event_end_time_a)).setText(event.getEndTime());
    }
    private void populateUpdateAd() {
        headTxt.setText("Edit Event");
        button.setText("Edit Event");
        isEdit = true;

        firestoreDB.collection("events").document(docId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                        Event e = doc.toObject(Event.class);
                        displayAdForUpdate(e);
                }
                 else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }
    private Event createEventObj(){
        final Event event = new Event();
        event.setName(((TextView)getActivity()
                .findViewById(R.id.event_name_a)).getText().toString());
        event.setPlace(((TextView)getActivity()
                .findViewById(R.id.event_place_a)).getText().toString());
        event.setType(((TextView)getActivity()
                .findViewById(R.id.event_type_a)).getText().toString());
        event.setStartTime(((TextView)getActivity()
                .findViewById(R.id.event_start_time_a)).getText().toString());
        event.setEndTime(((TextView)getActivity()
                .findViewById(R.id.event_end_time_a)).getText().toString());

        return event;
    }
    private void addDocumentToCollection(Event event){
        firestoreDB.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Event document added - id: "
                                + documentReference.getId());
                        restUi();
                        Toast.makeText(getActivity(),
                                "Event document has been added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding event document", e);
                        Toast.makeText(getActivity(),
                                "Event document could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateDocumentToCollection(Event event){
        firestoreDB.collection("events")
                .document(docId)
                .set(event, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Event document updated ");
                        Toast.makeText(getActivity(),
                                "Event document has been updated",
                                Toast.LENGTH_SHORT).show();
                        showEventScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding event document", e);
                        Toast.makeText(getActivity(),
                                "Event document could not be added",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void restUi(){
        ((TextView)getActivity()
                .findViewById(R.id.event_name_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.event_type_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.event_place_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.event_start_time_a)).setText("");
        ((TextView)getActivity()
                .findViewById(R.id.event_end_time_a)).setText("");
    }
    private void showEventScreen() {
        Intent i = new Intent();
        i.setClass(getActivity(), EventActivity.class);
        startActivity(i);
    }
}