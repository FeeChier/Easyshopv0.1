package com.example.easyshop;

import android.view.Display;

public class ModelMagasin {
    private String name;
    private String adress;
    private ModelMagasin (){}
    private ModelMagasin (String name, String adress) {
        this.name=name;
        this.adress=adress;
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
}
