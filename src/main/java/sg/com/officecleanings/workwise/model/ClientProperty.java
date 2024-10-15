package sg.com.officecleanings.workwise.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import sg.com.officecleanings.workwise.model.id.ClientPropertyId;

@Entity 
public class ClientProperty {

    @EmbeddedId
    private ClientPropertyId id;

    @ManyToOne
    @MapsId("clientId")
    @JoinColumn(name = "clientId", referencedColumnName = "clientId", nullable = false)
    private Client client;

    @ManyToOne
    @MapsId("propertyId")
    @JoinColumn(name = "propertyId", referencedColumnName = "propertyId", nullable = false)
    private Property property;

    public ClientProperty() {
    }

    public ClientProperty(Client client, Property property) {
        this.client = client;
        this.property = property;
        this.id = new ClientPropertyId(client.getClientId(), property.getPropertyId());
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ClientProperty))
            return false;
        ClientProperty that = (ClientProperty) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
