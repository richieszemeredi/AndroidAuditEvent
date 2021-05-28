package com.premble.androidauditevent;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.premble.androidauditevent.model.AuditEvent;

import java.util.Date;
import java.util.Map;

public class AuditEventActivity extends AppCompatActivity {
    private static final String TAG = "AddAuditEventActivity";

    Spinner typeSpinner;
    Spinner subtypeSpinner;
    Spinner actionSpinner;
    Spinner outcomeSpinner;
    TextView outcomeDescEdit;
    TextView purposeOfEventEdit;
    TextView agentEdit;
    TextView sourceEdit;
    TextView entityEdit;

    Button btAdd;

    private FirebaseFirestore firestoreDB;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditevent);

        typeSpinner = findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        subtypeSpinner = findViewById(R.id.subtypeSpinner);
        ArrayAdapter<CharSequence> subtypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.subtype, android.R.layout.simple_spinner_item);
        subtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subtypeSpinner.setAdapter(subtypeAdapter);
        actionSpinner = findViewById(R.id.actionSpinner);
        ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(this,
                R.array.action, android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(actionAdapter);
        outcomeSpinner = findViewById(R.id.outcomeSpinner);
        ArrayAdapter<CharSequence> outcomeAdapter = ArrayAdapter.createFromResource(this,
                R.array.outcome, android.R.layout.simple_spinner_item);
        outcomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outcomeSpinner.setAdapter(outcomeAdapter);
        outcomeDescEdit = findViewById(R.id.outcomeDescEdit);
        purposeOfEventEdit = findViewById(R.id.purposeOfEventEdit);
        agentEdit = findViewById(R.id.agentEdit);
        sourceEdit = findViewById(R.id.sourceEdit);
        entityEdit = findViewById(R.id.entityEdit);
        btAdd = findViewById(R.id.btAdd);



        firestoreDB = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("UpdateAuditEventId");
            typeSpinner.setSelection(bundle.getInt("UpdateAuditEventType"));
            subtypeSpinner.setSelection(bundle.getInt("UpdateAuditEventSubtype"));
            actionSpinner.setSelection(bundle.getInt("UpdateAuditEventAction"));
            outcomeSpinner.setSelection(bundle.getInt("UpdateAuditEventOutcome"));
            outcomeDescEdit.setText(bundle.getString("UpdateAuditEventOutcomeDesc"));
            purposeOfEventEdit.setText(bundle.getString("UpdateAuditEventPurposeOfEvent"));
            agentEdit.setText(bundle.getString("UpdateAuditEventAgent"));
            sourceEdit.setText(bundle.getString("UpdateAuditEventSource"));
            entityEdit.setText(bundle.getString("UpdateAuditEventEntity"));
        }

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outcomeDesc = outcomeDescEdit.getText().toString();
                String purposeOfEvent = purposeOfEventEdit.getText().toString();
                String agent = agentEdit.getText().toString();
                String source = sourceEdit.getText().toString();
                String entity = entityEdit.getText().toString();
                String type = typeSpinner.getSelectedItem().toString();
                String subtype = typeSpinner.getSelectedItem().toString();
                String action = typeSpinner.getSelectedItem().toString();
                String period = typeSpinner.getSelectedItem().toString();
                String outcome = typeSpinner.getSelectedItem().toString();
                Date recorded = new Date();
                if (id.length() > 0) {
                    updateAuditEvent(id, type, subtype, action, period, recorded, outcome, outcomeDesc, purposeOfEvent, agent, source, entity);
                } else {
                    addAuditEvent(type, subtype, action, period, recorded, outcome, outcomeDesc, purposeOfEvent, agent, source, entity);
                }
                finish();
            }
        });
    }

    private void updateAuditEvent(String id, String type, String subtype, String action, String period, Date recorded, String outcome, String outcomeDesc, String purposeOfEvent, String agent, String source, String entity) {
        Map auditEvent = new AuditEvent(id, type, subtype, action, period, recorded, outcome, outcomeDesc, purposeOfEvent, agent, source, entity).toMap();

        firestoreDB.collection("AuditEvent")
                .document(id)
                .set(auditEvent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "AuditEvent document update successful!");
                        Toast.makeText(getApplicationContext(), "AuditEvent has been updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding AuditEvent document", e);
                        Toast.makeText(getApplicationContext(), "AuditEvent could not be updated!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addAuditEvent(String type, String subtype, String action, String period, Date recorded, String outcome, String outcomeDesc, String purposeOfEvent, String agent, String source, String entity) {
        Map auditEvent = new AuditEvent(id, type, subtype, action, period, recorded, outcome, outcomeDesc, purposeOfEvent, agent, source, entity).toMap();

        firestoreDB.collection("AuditEvent")
                .add(auditEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "AuditEvent has been added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding AuditEvent document", e);
                        Toast.makeText(getApplicationContext(), "AuditEvent could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
