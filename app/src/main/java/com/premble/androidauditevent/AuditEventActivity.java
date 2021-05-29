package com.premble.androidauditevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.premble.androidauditevent.model.AuditEvent;

import java.util.Calendar;
import java.util.Map;

public class AuditEventActivity extends AppCompatActivity {
    private static final String TAG = "AddAuditEventActivity";

    EditText periodStart;
    EditText periodEnd;
    EditText recordedEdit;
    Spinner typeSpinner;
    Spinner subtypeSpinner;
    Spinner actionSpinner;
    Spinner outcomeSpinner;
    EditText outcomeDescEdit;
    EditText purposeOfEventEdit;
    EditText agentEdit;
    EditText sourceEdit;
    EditText entityEdit;
    DatePickerDialog pickPeriodStart;
    DatePickerDialog pickPeriodEnd;
    DatePickerDialog pickRecorded;

    Button btAdd;

    private FirebaseFirestore firestoreDB;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditevent);
        periodStart = findViewById(R.id.periodStart);
        periodStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                pickPeriodStart = new DatePickerDialog(AuditEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                periodStart.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                pickPeriodStart.show();
            }
        });
        periodEnd = findViewById(R.id.periodEnd);
        periodEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                pickPeriodEnd = new DatePickerDialog(AuditEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                periodEnd.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                pickPeriodEnd.show();
            }
        });
        recordedEdit = findViewById(R.id.recordedEdit);
        recordedEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                pickRecorded = new DatePickerDialog(AuditEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                recordedEdit.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                pickRecorded.show();
            }
        });
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
            typeSpinner.setSelection(typeAdapter.getPosition(bundle.getString("UpdateAuditEventType")));
            subtypeSpinner.setSelection(subtypeAdapter.getPosition(bundle.getString("UpdateAuditEventSubtype")));
            actionSpinner.setSelection(actionAdapter.getPosition(bundle.getString("UpdateAuditEventAction")));
            outcomeSpinner.setSelection(outcomeAdapter.getPosition(bundle.getString("UpdateAuditEventOutcome")));
            outcomeDescEdit.setText(bundle.getString("UpdateAuditEventOutcomeDesc"));
            purposeOfEventEdit.setText(bundle.getString("UpdateAuditEventPurposeOfEvent"));
            agentEdit.setText(bundle.getString("UpdateAuditEventAgent"));
            sourceEdit.setText(bundle.getString("UpdateAuditEventSource"));
            entityEdit.setText(bundle.getString("UpdateAuditEventEntity"));
            periodStart.setText(bundle.getString("UpdateAuditEventPeriod").split("-")[0]);
            periodEnd.setText(bundle.getString("UpdateAuditEventPeriod").split("-")[1]);
            recordedEdit.setText(bundle.getString("UpdateAuditEventRecorded"));
            System.out.println(bundle.getString("UpdateAuditEventRecorded"));
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
                String subtype = subtypeSpinner.getSelectedItem().toString();
                String action = actionSpinner.getSelectedItem().toString();
                String period = periodStart.getText().toString() + "-" + periodEnd.getText().toString();
                String outcome = outcomeSpinner.getSelectedItem().toString();
                String recorded = recordedEdit.getText().toString();
                if (id.length() > 0) {
                    updateAuditEvent(id, type, subtype, action, period, recorded, outcome, outcomeDesc, purposeOfEvent, agent, source, entity);
                } else {
                    addAuditEvent(type, subtype, action, period, recorded, outcome, outcomeDesc, purposeOfEvent, agent, source, entity);
                }
                finish();
            }
        });
    }

    private void updateAuditEvent(String id, String type, String subtype, String action, String period, String recorded, String outcome, String outcomeDesc, String purposeOfEvent, String agent, String source, String entity) {
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

    private void addAuditEvent(String type, String subtype, String action, String period, String recorded, String outcome, String outcomeDesc, String purposeOfEvent, String agent, String source, String entity) {
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
