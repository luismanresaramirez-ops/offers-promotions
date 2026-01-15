package canalplus.offres.offres.domain.model;

import java.math.BigDecimal;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "COD_GRATUIT")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodGratuit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cgratuit")
    private Long id;

    private String lgratuit;

    // prolonge la date de fin d'abonement car il est ajouté au milieu de l'abonement
    private Boolean indplus;
    // suspension temporaire d'un mois
    private Boolean indsusp;
    // mois offert à la fin de l'abonement
    private Boolean indflag;

    private BigDecimal cposte;
    private Boolean indfinabo;

    private String cqualificpt;

    @OneToMany(mappedBy = "codGratuit", fetch = FetchType.LAZY)
    private List<AboGratuit> aboGratuits = new ArrayList<>();

    @OneToMany(mappedBy = "codGratuit", fetch = FetchType.LAZY)
    private List<AvantGratuit> avantGratuits = new ArrayList<>();
}
