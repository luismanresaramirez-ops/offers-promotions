package canalplus.offres.offres.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Entity
@Table(name = "PROMOTION")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cpromo")
    private Long id;

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<PromoArticle> promoArticles = new ArrayList<>();

    @OneToMany(mappedBy = "promotion", fetch = FetchType.LAZY)
    private List<AvantGratuit> avantGratuits = new ArrayList<>();
    
}
