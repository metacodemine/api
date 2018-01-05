package org.eientei.codemine.api.data;

import java.util.List;

public class UserProperties {
    private String id;
    private String name;
    private List<UserProperty> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<UserProperty> properties) {
        this.properties = properties;
    }
}
