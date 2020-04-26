package com.example.pokedexapp;

import java.util.ArrayList;

public class PokemonListModel {
    private ArrayList<PokemonModel> pokemon;

    public static PokemonListModel getHardCodedPokemonList() {
        ArrayList<PokemonModel> pokemons = new ArrayList<PokemonModel>();
        pokemons.add(new PokemonModel(
                1, "Bulbasaur",
                new PokemonType[]{
                        new PokemonType("grass", "insert url here"),
                        new PokemonType("water", "insert url here"),
                }));
        pokemons.add(new PokemonModel(
                4, "Charmander",
                new PokemonType[]{new PokemonType("fire", "insert url here")}));
        pokemons.add(new PokemonModel(
                7, "Squirtle",
                new PokemonType[]{new PokemonType("water", "insert url here")}));
        pokemons.add(new PokemonModel(
                25, "Pikachu",
                new PokemonType[]{new PokemonType("electric", "insert url here")}));
        pokemons.add(new PokemonModel(
                147, "Dratini",
                new PokemonType[]{new PokemonType("dragon", "insert url here")}));
        pokemons.add(new PokemonModel(
                66, "Machop",
                new PokemonType[]{new PokemonType("fighting", "insert url here")}));
        pokemons.add(new PokemonModel(
                92, "Ghastly",
                new PokemonType[]{
                        new PokemonType("ghost", "insert url here"),
                        new PokemonType("poison", "insert url here")
                }));
        return new PokemonListModel(pokemons);
    }

    public PokemonListModel(ArrayList<PokemonModel> pokemon) {
        this.pokemon = pokemon;
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

}
