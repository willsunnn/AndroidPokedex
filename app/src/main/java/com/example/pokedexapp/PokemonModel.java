package com.example.pokedexapp;

import java.util.Arrays;

public class PokemonModel {
    public static final String API_CALL_URL = "https://pokeapi.co/api/v2/pokemon/";

    private int dexNumber;
    private String name;
    private PokemonType[] pokemonTypes;

    public static PokemonModel getPokemonFromDexNumber(int dexNumber) throws Exception {
        throw new Exception("this method hasn't been implemented yet");
    }

    public static String getApiCallUrl(int dexNumber) {
        return API_CALL_URL + Integer.toString(dexNumber) + "/";
    }

    public PokemonModel(int dexNumber, String name, PokemonType[] pokemonTypes) {
        this.dexNumber = dexNumber;
        this.name = name;
        this.pokemonTypes = pokemonTypes;
    }

    public String getName() {
        return this.name;
    }

    public PokemonType[] getTypes() {
        return this.pokemonTypes;
    }

    public int getSprite() {
        return R.drawable.bulbasaur;
    }

    public int getLevel() {
        return 5;
    }

    public String getTypesAsString() {
        String types = Arrays.toString(pokemonTypes);
        return types.substring(1, types.length()-1);
    }

}

class PokemonType {
    private String typeName;
    private String url;

    public PokemonType(String typeName, String infoUrl) {
        this.typeName = typeName;
        this.url = infoUrl;
    }

    public String getTypeName() {
        return typeName;
    }

    public String toString() {
        return typeName;
    }
}

class PokemonMove {
    private String moveName;
    private String url;

    public PokemonMove(String moveName, String infoUrl) {
        this.moveName = moveName;
        this.url = infoUrl;
    }

    public String getMoveName() {
        return moveName;
    }
}
