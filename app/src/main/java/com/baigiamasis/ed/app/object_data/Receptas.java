package com.baigiamasis.ed.app.object_data;

import java.util.ArrayList;

public class Receptas {
    private String rec_pav, apras, img_URL;
    private int rec_ID, ingredSk;
    private ArrayList<Ingredientas> ingredientas = new ArrayList<>();

    public Receptas() {
    }

    public Receptas(int rec_ID, String rec_pav, int amount) {
        this.rec_ID = rec_ID;
        this.rec_pav = rec_pav;
    }

    public int getIngredSk() {
        return ingredSk;
    }

    public void setIngredSk(int ingredSk) {
        this.ingredSk = ingredSk;
    }

    public String getPav() {
        return rec_pav;
    }

    public void setPav(String rec_pav) {
        this.rec_pav = rec_pav;
    }

    public String getApras() {
        return apras;
    }

    public void setApras(String apras) {
        this.apras = apras;
    }

    public int getId() {
        return rec_ID;
    }

    public void setId(int rec_ID) {
        this.rec_ID = rec_ID;
    }

    public ArrayList<Ingredientas> getIngredientas() {
        return ingredientas;
    }

    public void setIngredientas(Ingredientas ingredientas) {
        this.ingredientas.add(ingredientas);
    }
}