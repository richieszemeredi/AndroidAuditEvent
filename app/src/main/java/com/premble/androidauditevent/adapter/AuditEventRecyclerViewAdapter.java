package com.premble.androidauditevent.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import com.premble.androidauditevent.AuditEventActivity;
import com.premble.androidauditevent.R;
import com.premble.androidauditevent.model.AuditEvent;

import java.util.List;

public class AuditEventRecyclerViewAdapter extends RecyclerView.Adapter<AuditEventRecyclerViewAdapter.ViewHolder> {
    private List auditEventsList;
    private Context context;
    private FirebaseFirestore firestoreDB;

    public AuditEventRecyclerViewAdapter(List auditEventsList, Context context, FirebaseFirestore firestoreDB) {
        this.auditEventsList = auditEventsList;
        this.context = context;
        this.firestoreDB = firestoreDB;
    }

    @Override
    public AuditEventRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_auditevent, parent, false);

        return new AuditEventRecyclerViewAdapter.ViewHolder(view);
    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//    }

    @Override
    public void onBindViewHolder(AuditEventRecyclerViewAdapter.ViewHolder holder, int position) {
        final int itemPosition = position;
        final AuditEvent auditEvent = (AuditEvent) auditEventsList.get(itemPosition);

        holder.title.setText(auditEvent.getId());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAuditEvent(auditEvent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAuditEvent(auditEvent.getId(), itemPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return auditEventsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView edit;
        ImageView delete;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tvID);

            edit = view.findViewById(R.id.ivEdit);
            delete = view.findViewById(R.id.ivDelete);
        }
    }

    private void updateAuditEvent(AuditEvent auditEvent) {
        Intent intent = new Intent(context, AuditEventActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("UpdateAuditEventId", auditEvent.getId());
//        intent.putExtra("UpdateNoteTitle", note.getTitle());
//        intent.putExtra("UpdateNoteContent", note.getContent());
        context.startActivity(intent);
    }

    private void deleteAuditEvent(String id, final int position) {
        firestoreDB.collection("AuditEvent")
                .document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        auditEventsList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, auditEventsList.size());
                        Toast.makeText(context, "AuditEvent has been deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
