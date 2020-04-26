package com.example.pokedexapp;

import java.util.ArrayList;

public class PokemonListModel {
    private ArrayList<PokemonModel> pokemon;
    private ListModelUpdateListener listener;

    public static PokemonListModel getPokemonList(int[] dexNumbers) {
        PokemonListModel model = new PokemonListModel(new ArrayList<PokemonModel>());
        for(int dexNumber: dexNumbers) {
            PokemonAPIManager.getPokemonFromDexNumber(model, dexNumber);
        }
        return model;
    }

    public PokemonListModel(ArrayList<PokemonModel> pokemon) {
        this.pokemon = pokemon;
        this.listener = null;
    }

    public ArrayList<PokemonModel> getPokemonList() {
        return this.pokemon;
    }

    public int getNumPokemon() {
        return pokemon.size();
    }

    public PokemonModel getPokemonAtIndex(int i) {
        return pokemon.get(i);
    }

    // Methods used to handle asynchronous loading of data from API

    public void addPokemonToList(PokemonModel poke) {
        pokemon.add(poke);
        if(this.listener != null) {
            this.listener.onListModelUpdate(this);
        }
    }

    public void registerListModelUpdateListener(ListModelUpdateListener listener) {
        this.listener = listener;
    }

    interface ListModelUpdateListener {
        public void onListModelUpdate(PokemonListModel model);
    }
}
