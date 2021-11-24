package br.com.up.pokedexmobile.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable {

    private int id;
    private String nome;
    private String image;
    private ArrayList<String> types;
    private ArrayList<String> abilities;
    private ArrayList<String> moves;

    public Pokemon(int id, String nome, String image) {
        this.id = id;
        this.nome = nome;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
