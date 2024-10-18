package sg.com.officecleanings.workwise.model.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClientPropertyId implements Serializable {
    private Integer clientId;
    private Integer propertyId;

    // Equals, hashCode, constructors

    public ClientPropertyId() {
    }

    public ClientPropertyId(Integer clientId, Integer propertyId) {
        this.clientId = clientId;
        this.propertyId = propertyId;
    }
    
}

