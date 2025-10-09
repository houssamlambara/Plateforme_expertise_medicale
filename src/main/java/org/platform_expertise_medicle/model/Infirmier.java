package org.platform_expertise_medicle.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INFERMIER")
public class Infirmier extends User {
    public Infirmier() {}
}
