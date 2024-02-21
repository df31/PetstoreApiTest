package models;

import lombok.Getter;
@Getter
public enum Status {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");
    private final String value;
    Status(String value) {
        this.value = value;
    }
}
