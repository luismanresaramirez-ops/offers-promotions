package canalplus.offres.offres.domain.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ARTICLE_MINEUR")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleMineur {
	@EmbeddedId
    private ArticleMineurId id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carticle")
    @JoinColumn(name = "carticle")
    private Article article;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cmineur")
    @JoinColumn(name = "cmineur")
    private Mineur mineur;
}
