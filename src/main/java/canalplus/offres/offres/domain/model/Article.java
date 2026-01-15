package canalplus.offres.offres.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ARTICLE")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carticle")
    private Long id;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prix;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<PromoArticle> promoArticles = new ArrayList<>();
}
