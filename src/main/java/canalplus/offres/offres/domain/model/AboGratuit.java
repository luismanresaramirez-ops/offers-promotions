package canalplus.offres.offres.domain.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ABO_GRATUIT")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboGratuit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_abo_gratuit")
    private Long id;

    @Column(nullable = false)
    private Long numContrat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cgratuit", nullable = false)
    private CodGratuit codGratuit;

    @Column(nullable = false)
    private LocalDate dateDebut;

    private LocalDate dateFin;
}
