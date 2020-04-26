package com.example.pokedexapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class PokemonModel implements Parcelable {
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

    public int getDexNumber() {
        return this.dexNumber;
    }

    public String getTypesAsString() {
        String types = Arrays.toString(pokemonTypes);
        return types.substring(1, types.length()-1);
    }

    public int getWeight() {
        return -1;
    }

    public int getHeight() {
        return -1;
    }

    public int getFrontSprite() {
        return R.drawable.bulbasaur;
    }

    public int getBackSprite() {
        return R.drawable.bulbasaur_back;
    }

    public int getLevel() {
        return 5;
    }

    public PokemonMove[] getMoves() {
        return new PokemonMove[]{
                new PokemonMove("Razor Leaf", "shoots leaves"),
                new PokemonMove("Razor Leaf2", "shoots leaves"),
                new PokemonMove("Razor Leaf3", "shoots leaves"),
                new PokemonMove("Razor Leaf4", "shoots leaves"),
                new PokemonMove("Razor Leaf5", "shoots leaves"),
                new PokemonMove("Razor Leaf6", "shoots leaves"),
                new PokemonMove("Razor Leaf7", "this one has a longer description hard coded in to test the text wrapping on the text view"),
                new PokemonMove("Razor Leaf8", "shoots leaves"),
                new PokemonMove("Razor Leaf9", "shoots leaves"),
                new PokemonMove("Razor Leaf10", "shoots leaves"),
                new PokemonMove("Razor Leaf11", "shoots leaves"),
                new PokemonMove("Razor Leaf12", "shoots leaves"),
                new PokemonMove("Razor Leaf13", "shoots leaves"),
        };
    }

    public PokemonAbility[] getAbilities() {
        return new PokemonAbility[]{
                new PokemonAbility("Chlorophyll", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll2", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll3", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll4", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll5", "this one also has a longer description hard coded in to test the text warpping on the text view"),new PokemonAbility("Chlorophyll", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll6", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll7", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll8", "speed is raised in the sun"),
                new PokemonAbility("Chlorophyll9", "speed is raised in the sun"),
        };
    }

    // Parcelable interface methods

    protected PokemonModel(Parcel in) {
        dexNumber = in.readInt();
        name = in.readString();
        pokemonTypes = in.createTypedArray(PokemonType.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dexNumber);
        dest.writeString(name);
        dest.writeTypedArray(pokemonTypes, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PokemonModel> CREATOR = new Creator<PokemonModel>() {
        @Override
        public PokemonModel createFromParcel(Parcel in) {
            return new PokemonModel(in);
        }

        @Override
        public PokemonModel[] newArray(int size) {
            return new PokemonModel[size];
        }
    };


}

class PokemonType implements Parcelable {
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

    // Parcelable interface methods

    protected PokemonType(Parcel in) {
        typeName = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeName);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PokemonType> CREATOR = new Creator<PokemonType>() {
        @Override
        public PokemonType createFromParcel(Parcel in) {
            return new PokemonType(in);
        }

        @Override
        public PokemonType[] newArray(int size) {
            return new PokemonType[size];
        }
    };
}

class PokemonMove implements Parcelable {
    private String moveName;
    private String moveDescription;

    public PokemonMove(String moveName, String moveDescription) {
        this.moveName = moveName;
        this.moveDescription = moveDescription;
    }

    public String getMoveName() {
        return moveName;
    }

    public String getMoveDescription() {
        return moveDescription;
    }

    // Parcelable interface methods

    protected PokemonMove(Parcel in) {
        moveName = in.readString();
        moveDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moveName);
        dest.writeString(moveDescription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PokemonMove> CREATOR = new Creator<PokemonMove>() {
        @Override
        public PokemonMove createFromParcel(Parcel in) {
            return new PokemonMove(in);
        }

        @Override
        public PokemonMove[] newArray(int size) {
            return new PokemonMove[size];
        }
    };
}

class PokemonAbility implements Parcelable {
    private String name;
    private String description;

    public PokemonAbility(String abilityName, String abilityDescription) {
        this.name = abilityName;
        this.description = abilityDescription;
    }

    public String getAbilityName() {
        return name;
    }

    public String getAbilityDescription() {
        return description;
    }

    // Parcelable interface methods

    protected PokemonAbility(Parcel in) {
        name = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PokemonAbility> CREATOR = new Creator<PokemonAbility>() {
        @Override
        public PokemonAbility createFromParcel(Parcel in) {
            return new PokemonAbility(in);
        }

        @Override
        public PokemonAbility[] newArray(int size) {
            return new PokemonAbility[size];
        }
    };
}