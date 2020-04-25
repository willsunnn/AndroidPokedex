package com.example.pokedexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder> {
    private PokemonListModel pokemons;

    public PokemonListAdapter(PokemonListModel pokemons) {
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View pokemonView = inflater.inflate(R.layout.pokemon_list_subview, parent, false);
        return new PokemonViewHolder(pokemonView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonModel pokemon = pokemons.getPokemonAtIndex(position);
        holder.spriteView.setImageResource(pokemon.getSprite());
        holder.nameView.setText("Name: "+pokemon.getName());
        holder.levelView.setText("Level "+Integer.toString(pokemon.getLevel()));
        holder.typeView.setText("Types: "+pokemon.getTypesAsString());
    }

    @Override
    public int getItemCount() {
        return pokemons.getNumPokemon();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        ImageView spriteView;
        TextView nameView;
        TextView levelView;
        TextView typeView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
            spriteView = itemView.findViewById(R.id.SpriteView);
            nameView = itemView.findViewById(R.id.NameView);
            levelView = itemView.findViewById(R.id.LevelView);
            typeView = itemView.findViewById(R.id.TypeView);
        }
    }

}
