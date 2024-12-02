package de.hsos.swa.boundary.dto;

public class KundeWebDTOId extends KundeWebDTO {
    private Long id;

    public KundeWebDTOId(Long id, String name) {
        super(name);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
