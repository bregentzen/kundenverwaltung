package de.hsos.swa.boundary.dto;

public class KundeWebDTO {
    String name;

    public KundeWebDTO() {
    }

    public KundeWebDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
