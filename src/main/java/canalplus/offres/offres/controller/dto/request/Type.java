package canalplus.offres.offres.controller.dto.request;

public enum Type {

    MINEUR("MINEUR"),
    MAJEUR("MAJEUR");

    private final String libelle;

    Type(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
