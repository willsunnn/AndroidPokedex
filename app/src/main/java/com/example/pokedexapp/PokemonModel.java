package com.example.pokedexapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Arrays;

public class PokemonModel implements Parcelable {

    public static final int DEFAULT_LEVEL = 10;

    private int dexNumber;
    private String name;
    private String[] pokemonTypes;
    private PokemonMove[] pokemonMoves;
    private PokemonAbility[] pokemonAbilities;
    private int weight;
    private int height;
    private String frontSpriteURL;
    private String backSpriteURL;
    private int level;

    public PokemonModel(int dexNumber, String name,
                        String[] pokemonTypes,
                        PokemonMove[] pokemonMoves,
                        PokemonAbility[] pokemonAbilities,
                        int weight,
                        int height,
                        String frontSprite,
                        String backSprite,
                        int level) {
        this.dexNumber = dexNumber;
        this.name = name;
        this.pokemonTypes = pokemonTypes;
        this.pokemonMoves = pokemonMoves;
        this.pokemonAbilities = pokemonAbilities;
        this.weight = weight;
        this.height = height;
        this.frontSpriteURL = frontSprite;
        this.backSpriteURL = backSprite;
        this.level = level;
    }

    public String getName() {
        return upperCaseFirstLetter(name);
    }

    public int getDexNumber() {
        return this.dexNumber;
    }

    public String getTypesAsString() {
        String[] upperCaseTypes = new String[pokemonTypes.length];
        for(int i=0; i<pokemonTypes.length; ++i) {
            upperCaseTypes[i] = upperCaseFirstLetter(pokemonTypes[i]);
        }
        return TextUtils.join(", ", upperCaseTypes);
    }

    private String upperCaseFirstLetter(String s) {
        if(s==null || s.equals("")){
            return s;
        }
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getFrontSprite() {
        return frontSpriteURL;
    }

    public String getBackSprite() {
        return backSpriteURL;
    }

    public int getLevel() {
        return level;
    }

    public PokemonMove[] getMoves() {
        return this.pokemonMoves;
    }

    public PokemonAbility[] getAbilities() {
        return this.pokemonAbilities;
    }

    // Parcelable interface methods

    protected PokemonModel(Parcel in) {
        dexNumber = in.readInt();
        name = in.readString();
        pokemonTypes = in.createStringArray();
        pokemonMoves = in.createTypedArray(PokemonMove.CREATOR);
        pokemonAbilities = in.createTypedArray(PokemonAbility.CREATOR);
        weight = in.readInt();
        height = in.readInt();
        frontSpriteURL = in.readString();
        backSpriteURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dexNumber);
        dest.writeString(name);
        dest.writeStringArray(pokemonTypes);
        dest.writeTypedArray(pokemonMoves, flags);
        dest.writeTypedArray(pokemonAbilities, flags);
        dest.writeInt(weight);
        dest.writeInt(height);
        dest.writeString(frontSpriteURL);
        dest.writeString(backSpriteURL);
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

class PokemonMove implements Parcelable {
    private String moveName;
    private int moveId;
    private String moveDescription;
    MoveModelUpdateListener listener;

    public PokemonMove(String moveName, int moveId) {
        this.moveName = moveName;
        this.moveId = moveId;
        this.moveDescription = "";
        this.listener = null;
    }

    public String getMoveName() {
        return moveName;
    }

    public int getMoveId() {
        return moveId;
    }

    public String getMoveDescription() {
        return moveDescription;
    }

    // Methods used to handle asynchronous loading of data from API

    public void loadDescriptionFromApi() {
        PokemonAPIManager.getMoveDescriptionFromId(this);
    }

    public void setDescription(String description) {
        this.moveDescription = description;
        if(this.listener != null) {
            this.listener.onMoveModelUpdate(this);
        }
    }

    public void registerMoveModelUpdateListener(MoveModelUpdateListener listener) {
        this.listener = listener;
    }

    public void removeMoveModelUpdateListener() {
        this.listener = null;
    }

    public interface MoveModelUpdateListener {
        public void onMoveModelUpdate(PokemonMove move);
    }

    // Parcelable interface methods

    protected PokemonMove(Parcel in) {
        moveName = in.readString();
        moveId = in.readInt();
        moveDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moveName);
        dest.writeInt(moveId);
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
    private String abilityName;
    private int abilityId;
    private String abilityDescription;
    private AbilityModelUpdateListener listener;

    public PokemonAbility(String abilityName, int abilityId) {
        this.abilityName = abilityName;
        this.abilityId = abilityId;
        this.abilityDescription = "";
        this.listener = null;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public int getAbilityId() {
        return abilityId;
    }

    public String getAbilityDescription() {
        return abilityDescription;
    }

    // Methods used to handle asynchronous loading of data from API

    public void loadDescriptionFromApi() {
        PokemonAPIManager.getAbilityDescriptionFromId(this);
    }

    public void setDescription(String description) {
        this.abilityDescription = description;
        if(this.listener != null) {
            this.listener.onAbilityModelUpdate(this);
        }
    }

    public void registerAbilityModelUpdateListener(AbilityModelUpdateListener listener) {
        this.listener = listener;
    }

    public void removeAbilityModelUpdateListener() {
        this.listener = null;
    }

    public interface AbilityModelUpdateListener {
        public void onAbilityModelUpdate(PokemonAbility ability);
    }

    // Parcelable interface methods

    protected PokemonAbility(Parcel in) {
        abilityName = in.readString();
        abilityId = in.readInt();
        abilityDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(abilityName);
        dest.writeInt(abilityId);
        dest.writeString(abilityDescription);
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
