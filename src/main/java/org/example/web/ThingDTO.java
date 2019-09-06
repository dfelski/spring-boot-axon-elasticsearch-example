package org.example.web;

import java.util.UUID;

class ThingDTO {

    private UUID id;
    private String name;

    ThingDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
