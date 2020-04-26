package com.example.pokedexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PokemonListAdapter
        extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>
        implements Filterable {

    private PokemonListModel pokemon;
    private PokemonListModel filteredPokemon;
    private OnPokemonClickListener pokemonClickListener;

    public PokemonListAdapter(PokemonListModel pokemon, OnPokemonClickListener pokemonClickListener) {
        this.pokemon = pokemon;
        this.filteredPokemon = pokemon;
        this.pokemonClickListener = pokemonClickListener;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View pokemonView = inflater.inflate(R.layout.pokemon_list_subview, parent, false);
        return new PokemonViewHolder(pokemonView, this.pokemonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokemonModel poke = filteredPokemon.getPokemonAtIndex(position);
        holder.spriteView.setImageResource(poke.getFrontSprite());
        holder.nameView.setText("Name: "+poke.getName());
        holder.levelView.setText("Level "+Integer.toString(poke.getLevel()));
        holder.typeView.setText("Types: "+poke.getTypesAsString());
    }

    @Override
    public int getItemCount() {
        return filteredPokemon.getNumPokemon();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<PokemonModel> filtered = new ArrayList<PokemonModel>();
                for(PokemonModel poke: pokemon.getPokemonList()) {
                    if(poke.getName().toLowerCase().contains(constraint)) {
                        filtered.add(poke);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredPokemon = new PokemonListModel((ArrayList<PokemonModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnPokemonClickListener pokemonClickListener;

        ImageView spriteView;
        TextView nameView;
        TextView levelView;
        TextView typeView;

        public PokemonViewHolder(@NonNull View itemView, OnPokemonClickListener pokemonClickListener) {
            super(itemView);
            spriteView = itemView.findViewById(R.id.SpriteView);
            nameView = itemView.findViewById(R.id.NameView);
            levelView = itemView.findViewById(R.id.LevelView);
            typeView = itemView.findViewById(R.id.TypeView);

            this.pokemonClickListener = pokemonClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PokemonModel poke = filteredPokemon.getPokemonAtIndex(getAdapterPosition());
            this.pokemonClickListener.onPokemonClick(poke);
        }
    }

    public interface OnPokemonClickListener {
        void onPokemonClick(PokemonModel pokemon);
    }

}
