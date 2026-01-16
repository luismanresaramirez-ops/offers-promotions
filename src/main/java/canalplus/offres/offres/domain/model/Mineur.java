package canalplus.offres.offres.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MINEUR")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mineur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmineur")
    private Long id;
    
    @Column(nullable = false)
    private String designation;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;
    
    @OneToMany(
            mappedBy = "mineur",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private Set<ArticleMineur> articleMajeurs = new HashSet<>();
}
