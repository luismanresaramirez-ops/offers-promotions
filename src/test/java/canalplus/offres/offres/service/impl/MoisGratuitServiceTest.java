package canalplus.offres.offres.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import canalplus.offres.offres.domain.model.AvantGratuit;
import canalplus.offres.offres.domain.model.CodGratuit;

class MoisGratuitServiceTest {

    private MoisGratuitService service;

    @BeforeEach
    void setUp() {
        // Dépendances non utilisées par computeDateFin → null assumé
        service = new MoisGratuitService(null, null, null);
    }

    @Test
    void computeDateFin_should_extend_end_date_when_indplus_is_true() {
        // GIVEN
        LocalDate finAbonnement = LocalDate.of(2025, 1, 31);

        AvantGratuit avantGratuit = AvantGratuit.builder()
                .nbrMois(2)
                .build();

        CodGratuit codGratuit = CodGratuit.builder()
                .indplus(true)
                .avantGratuits(List.of(avantGratuit))
                .build();

        // WHEN
        LocalDate result = service.computeDateFin(
                codGratuit,
                LocalDate.of(2025, 1, 1),
                finAbonnement
        );

        // THEN
        assertThat(result).isEqualTo(LocalDate.of(2025, 3, 31));
    }

    
    @Test
    void computeDateFin_should_extend_end_date_when_indplus_is_true2() {
        // GIVEN
        LocalDate finAbonnement = LocalDate.of(2026, 1, 31);

        AvantGratuit avantGratuit = AvantGratuit.builder()
                .nbrMois(5)
                .build();

        CodGratuit codGratuit = CodGratuit.builder()
                .indplus(true)
                .avantGratuits(List.of(avantGratuit))
                .build();

        // WHEN
        LocalDate result = service.computeDateFin(
                codGratuit,
                LocalDate.of(2026, 1, 1),
                finAbonnement
        );

        // THEN
        assertThat(result).isEqualTo(LocalDate.of(2026, 6, 30));
    }

    @Test
    void computeDateFin_should_return_same_end_date_when_indplus_is_false() {
        // GIVEN
        LocalDate finAbonnement = LocalDate.of(2025, 1, 31);

        CodGratuit codGratuit = CodGratuit.builder()
                .indplus(false)
                .avantGratuits(List.of(
                        AvantGratuit.builder().nbrMois(3).build()
                ))
                .build();

        // WHEN
        LocalDate result = service.computeDateFin(
                codGratuit,
                LocalDate.of(2025, 1, 1),
                finAbonnement
        );

        // THEN
        assertThat(result).isEqualTo(finAbonnement);
    }

    @Test
    void computeDateFin_should_return_same_end_date_when_indplus_is_null() {
        // GIVEN
        LocalDate finAbonnement = LocalDate.of(2025, 6, 15);

        CodGratuit codGratuit = CodGratuit.builder()
                .indplus(null)
                .avantGratuits(List.of(
                        AvantGratuit.builder().nbrMois(6).build()
                ))
                .build();

        // WHEN
        LocalDate result = service.computeDateFin(
                codGratuit,
                LocalDate.of(2025, 1, 1),
                finAbonnement
        );

        // THEN
        assertThat(result).isEqualTo(finAbonnement);
    }
}
