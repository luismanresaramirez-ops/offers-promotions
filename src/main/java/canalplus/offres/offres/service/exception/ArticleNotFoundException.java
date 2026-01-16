package canalplus.offres.offres.service.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(Long id) {
        super("Article majeur introuvable pour l'id : " + id);
    }
}