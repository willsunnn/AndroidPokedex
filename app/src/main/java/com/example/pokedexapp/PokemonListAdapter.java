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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokemonListAdapter
        extends RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>
        implements Filterable, PokemonListModel.ListModelUpdateListener {

    private PokemonListModel pokemon;
    private PokemonListModel filteredPokemon;
    private String searchString;
    private OnPokemonClickListener pokemonClickListener;

    public PokemonListAdapter(PokemonListModel pokemon, OnPokemonClickListener pokemonClickListener) {
        this.pokemon = pokemon;
        pokemon.registerListModelUpdateListener(this);
        this.filteredPokemon = pokemon;
        this.searchString = "";
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
        Picasso.get().load(poke.getFrontSprite()).into(holder.spriteView);
        holder.nameView.setText("Name: "+poke.getName());
        holder.levelView.setText("Level "+Integer.toString(poke.getLevel()));
        holder.typeView.setText("Types: "+poke.getTypesAsString());
    }

    @Override
    public int getItemCount() {
        return filteredPokemon.getNumPokemon();
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

    // Filterable interface

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<PokemonModel> filtered = new ArrayList<PokemonModel>();
                searchString = constraint.toString().toLowerCase();
                for(PokemonModel poke: pokemon.getPokemonList()) {
                    if(poke.getName().toLowerCase().contains(searchString)) {
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

    // PokemonListModel.ModelUpdateListener interface

    @Override
    public void onListModelUpdate(PokemonListModel model) {
        // a new PokemonModel has been added, so it should re-render the list and filter it
        this.getFilter().filter(searchString);
    }

}
