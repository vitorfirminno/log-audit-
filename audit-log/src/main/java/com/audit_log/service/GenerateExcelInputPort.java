package com.audit_log.service;


import java.util.List;

public interface GenerateExcelInputPort<T> {
    public byte[] generate(List<T> entity);
}
