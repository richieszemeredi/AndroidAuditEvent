package com.premble.androidauditevent.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuditEvent {
    private String id;
    private String type;
    private String subtype;
    private String action;
    private String period;
    private Date recorded;
    private String outcome;
    private String outcomeDesc;
    private String purposeOfEvent;
    private String agent;
    private String source;
    private String entity;

    public AuditEvent() {}

    public AuditEvent(String id, String type, String subtype, String action, String period, Date recorded, String outcome, String outcomeDesc, String purposeOfEvent, String agent, String source, String entity) {
        this.id = id;
        this.type = type;
        this.subtype = subtype;
        this.action = action;
        this.period = period;
        this.recorded = recorded;
        this.outcome = outcome;
        this.outcomeDesc = outcomeDesc;
        this.purposeOfEvent = purposeOfEvent;
        this.agent = agent;
        this.source = source;
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Date getRecorded() {
        return recorded;
    }

    public void setRecorded(Date recorded) {
        this.recorded = recorded;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcomeDesc() {
        return outcomeDesc;
    }

    public void setOutcomeDesc(String outcomeDesc) {
        this.outcomeDesc = outcomeDesc;
    }

    public String getPurposeOfEvent() {
        return purposeOfEvent;
    }

    public void setPurposeOfEvent(String purposeOfEvent) {
        this.purposeOfEvent = purposeOfEvent;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Map toMap() {

        HashMap result = new HashMap<>();
        result.put("id", this.id);
        result.put("type", this.type);
        result.put("subtype", this.subtype);
        result.put("action", this.action);
        result.put("period", this.period);
        result.put("recorded", this.recorded);
        result.put("outcome", this.outcome);
        result.put("outcomeDesc", this.outcomeDesc);
        result.put("purposeOfEvent", this.purposeOfEvent);
        result.put("agent", this.agent);
        result.put("source", this.source);
        result.put("entity", this.entity);

        return result;
    }
}
