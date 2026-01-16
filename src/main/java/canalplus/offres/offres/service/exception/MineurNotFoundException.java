package canalplus.offres.offres.service.exception;

public class MineurNotFoundException extends RuntimeException {
    public MineurNotFoundException(Long id) {
        super("Mineur introuvable pour l'id : " + id);
    }
}