package com.example.auditinglogging.model;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;

@Component
public class CustomAuditingEntityListener {
    private final ObjectFactory<AuditingHandler> handler;

    public CustomAuditingEntityListener(ObjectFactory<AuditingHandler> handler) {
        this.handler = handler;
    }

//    @Override
//    @PrePersist
//    public void touchForCreate(Object target) {
//        if (//custom logic) {
//                AuditingHandler object = handler.getObject();
//        if (object != null) {
//            object.markCreated(target);
//        }
//
//    }
}
