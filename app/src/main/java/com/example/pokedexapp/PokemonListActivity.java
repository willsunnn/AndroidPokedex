package com.example.pokedexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

public class PokemonListActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,  PokemonListAdapter.OnPokemonClickListener {
    PokemonListAdapter adapter;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokemon_list_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);

        // Setup the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup the SearchView
        final SearchView search = findViewById(R.id.pokemon_list_search);
        search.setOnQueryTextListener(this);

        // Setup the recyclerview
        recyclerView = findViewById(R.id.PokemonListView);
        PokemonListModel pokemon = PokemonListModel.getHardCodedPokemonList();
        adapter = new PokemonListAdapter(pokemon, this);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onPokemonClick(PokemonModel poke) {
        Intent intent = new Intent(this, PokemonDetailActivity.class);
        intent.putExtra("pokemon", poke);
        startActivity(intent);
    }
}
