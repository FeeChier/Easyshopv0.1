package com.example.easyshop;

import android.view.Display;

public class ModelMagasin {
    private String item_id;
    private String name;
    private String adress;

    private ModelMagasin() {
    }

    private ModelMagasin(String name, String adress, String item_id) {
        this.name = name;
        this.adress = adress;
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }
}
