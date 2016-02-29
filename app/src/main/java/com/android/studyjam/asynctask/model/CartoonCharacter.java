package com.android.studyjam.asynctask.model;

public class CartoonCharacter {

    public String objectId;
    public String name;
    public CharacterImage image;

    public CartoonCharacter() {
        
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharacterImage getImage() {
        return image;
    }

    public void setImage(CharacterImage image) {
        this.image = image;
    }
}
