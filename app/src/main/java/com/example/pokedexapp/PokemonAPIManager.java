package com.example.pokedexapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PokemonAPIManager {

    public static final String POKEMON_API_URL = "https://pokeapi.co";

    public static void getPokemonFromDexNumber(final PokemonListModel pokemonList, int dexNumber) {
        PokemonAPIService service = PokemonAPIManager.getPokemonModelRetrofit().create(PokemonAPIService.class);
        Call<PokemonModel> call = service.getPokemon(dexNumber);

        call.enqueue(new Callback<PokemonModel>() {
            @Override
            public void onResponse(Call<PokemonModel> call, Response<PokemonModel> response) {
                pokemonList.addPokemonToList(response.body());
            }

            @Override
            public void onFailure(Call<PokemonModel> call, Throwable t) {
                Log.d("tag",t.getMessage());
            }
        });
    }

    public static void getMoveDescriptionFromId(final PokemonMove move) {
        int moveId = move.getMoveId();
        PokemonAPIService service = PokemonAPIManager.getPokemonMoveRetrofit().create(PokemonAPIService.class);
        Call<String> call = service.getMoveDescription(moveId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                move.setDescription(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("tag",t.getMessage());
            }
        });
    }

    public static void getAbilityDescriptionFromId(final PokemonAbility ability) {
        int abilityId = ability.getAbilityId();
        PokemonAPIService service = PokemonAPIManager.getPokemonAbilityRetrofit().create(PokemonAPIService.class);
        Call<String> call = service.getAbilityDescription(abilityId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ability.setDescription(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("tag",t.getMessage());
            }
        });
    }

    private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(type, typeAdapter);
        Gson gson = builder.create();
        return GsonConverterFactory.create(gson);
    }

    private static Retrofit pokemonModelRetrofit;
    private static Retrofit pokemonMoveRetrofit;
    private static Retrofit pokemonAbilityRetrofit;

    public static Retrofit getPokemonModelRetrofit() {
        if(pokemonModelRetrofit==null) {
            pokemonModelRetrofit = getRetrofitInstance(PokemonModel.class, new PokemonModelDeserializer());
        }
        return pokemonModelRetrofit;
    }

    public static Retrofit getPokemonMoveRetrofit() {
        if(pokemonMoveRetrofit==null) {
            pokemonMoveRetrofit = getRetrofitInstance(String.class, new PokemonMoveDeserializer());
        }
        return pokemonMoveRetrofit;
    }

    public static Retrofit getPokemonAbilityRetrofit() {
        if(pokemonAbilityRetrofit==null) {
            pokemonAbilityRetrofit = getRetrofitInstance(String.class, new PokemonAbilityDeserializer());
        }
        return pokemonAbilityRetrofit;
    }

    public static Retrofit getRetrofitInstance(Type type, Object typeAdapter) {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(POKEMON_API_URL)
                .addConverterFactory(createGsonConverter(type, typeAdapter))
                .build();
    }

    public interface PokemonAPIService {
        @GET("/api/v2/pokemon/{dexNumber}/")
        Call<PokemonModel> getPokemon(@Path("dexNumber") int dexNumber);

        @GET("/api/v2/move/{moveId}/")
        Call<String> getMoveDescription(@Path("moveId") int moveId);

        @GET("/api/v2/ability/{abilityId}/")
        Call<String> getAbilityDescription(@Path("abilityId") int abilityId);
    }

    public static class PokemonModelDeserializer implements JsonDeserializer<PokemonModel> {
        @Override
        public PokemonModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject jsonObject = json.getAsJsonObject();

            int dexNumber = jsonObject.get("id").getAsInt();
            String name = jsonObject.get("name").getAsString();

            JsonArray jsonTypes = jsonObject.get("types").getAsJsonArray();
            String[] types = new String[jsonTypes.size()];
            for(JsonElement type: jsonTypes) {
                JsonObject jsonType = type.getAsJsonObject();
                int slot = jsonType.get("slot").getAsInt();
                String typeName = jsonType.get("type").getAsJsonObject().get("name").getAsString();
                types[slot-1] = typeName;
            }

            JsonArray jsonMoves = jsonObject.get("moves").getAsJsonArray();
            PokemonMove[] moves = new PokemonMove[jsonMoves.size()];
            for(int i=0; i<jsonMoves.size(); i++) {
                JsonObject jsonMove = jsonMoves.get(i).getAsJsonObject().get("move").getAsJsonObject();
                String moveName = jsonMove.get("name").getAsString();
                String[] moveURL = jsonMove.get("url").getAsString().split("/");
                String moveId = moveURL[moveURL.length-1];
                moves[i] = new PokemonMove(moveName, Integer.parseInt(moveId));
            }

            JsonArray jsonAbilities = jsonObject.get("abilities").getAsJsonArray();
            PokemonAbility[] abilities = new PokemonAbility[jsonAbilities.size()];
            for(int i=0; i<jsonAbilities.size(); i++) {
                JsonObject jsonAbility = jsonAbilities.get(i).getAsJsonObject().get("ability").getAsJsonObject();
                String abilityName = jsonAbility.get("name").getAsString();
                String[] abilityURL = jsonAbility.get("url").getAsString().split("/");
                String abilityId = abilityURL[abilityURL.length-1];
                abilities[i] = new PokemonAbility(abilityName, Integer.parseInt(abilityId));
            }

            int weight = jsonObject.get("weight").getAsInt();
            int height = jsonObject.get("height").getAsInt();
            String frontSprite = jsonObject.get("sprites").getAsJsonObject().get("front_default").getAsString();
            String backSprite = jsonObject.get("sprites").getAsJsonObject().get("back_default").getAsString();

            return new PokemonModel(dexNumber, name, types, moves, abilities, weight, height, frontSprite, backSprite, PokemonModel.DEFAULT_LEVEL);
        }
    }

    public static class PokemonMoveDeserializer implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray moveDescriptions = json.getAsJsonObject().get("flavor_text_entries").getAsJsonArray();
            for(JsonElement e: moveDescriptions) {
                JsonObject moveDescription = e.getAsJsonObject();
                if(moveDescription.get("language").getAsJsonObject().get("name").getAsString().equals("en")) {
                    return moveDescription.get("flavor_text").getAsString().replace("\n", "");
                }
            }
            return "could not load move description";
        }
    }

    public static class PokemonAbilityDeserializer implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray abilityDescriptions = json.getAsJsonObject().get("flavor_text_entries").getAsJsonArray();
            for(JsonElement e: abilityDescriptions) {
                JsonObject abilityDescription = e.getAsJsonObject();
                if(abilityDescription.get("language").getAsJsonObject().get("name").getAsString().equals("en")) {
                    return abilityDescription.get("flavor_text").getAsString().replace("\n", "");
                }
            }
            return "could not load ability description";
        }
    }
}
