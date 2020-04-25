package com.example.pokedexapp;

public class PokemonModel {
    public static final String API_CALL_URL = "https://pokeapi.co/api/v2/pokemon/";

    private int dexNumber;
    private String name;
    private PokemonType[] pokemonTypes;

    public static PokemonModel getPokemonFromDexNumber(int dexNumber) throws Exception {
        throw new Exception("this method hasn't been implemented yet");
    }

    public PokemonModel(int dexNumber, String name, PokemonType[] pokemonTypes) {
        this.dexNumber = dexNumber;
        this.name = name;
        this.pokemonTypes = pokemonTypes;
    }

    public static String getApiCallUrl(int dexNumber) {
        return API_CALL_URL + Integer.toString(dexNumber) + "/";
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
