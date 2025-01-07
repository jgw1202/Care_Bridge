package org.example.carebridge.domain.clinic.enumClass;

public enum ClinicStatus {
    NORMAL("normal"), DELETED("deleted");

    private String name;

    ClinicStatus(String name) {
        this.name = name;
    }
}
