package org.eientei.codemine.api.data;

import java.util.Map;

public class TextureProperties {
    private Long timestamp;
    private String profileId;
    private String profileName;
    private Map<String, TextureProperty> textures;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Map<String, TextureProperty> getTextures() {
        return textures;
    }

    public void setTextures(Map<String, TextureProperty> textures) {
        this.textures = textures;
    }
}
