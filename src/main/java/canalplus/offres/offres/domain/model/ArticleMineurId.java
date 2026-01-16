package canalplus.offres.offres.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleMineurId implements Serializable {

    @Column(name = "carticle")
    private Long carticle;

    @Column(name = "cmineur")
    private Long cmineur;
    
}
