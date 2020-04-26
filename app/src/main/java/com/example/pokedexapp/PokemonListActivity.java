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

import java.util.HashSet;
import java.util.Set;

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

        // Setup the model
        PokemonListModel pokemon = getRandomPokemon(7,1,151);

        // Setup the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup the SearchView
        final SearchView search = findViewById(R.id.pokemon_list_search);
        search.setOnQueryTextListener(this);

        // Setup the recyclerview
        recyclerView = findViewById(R.id.PokemonListView);
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

    // SearchView.OnQueryTextListener interface

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

    // PokemonListAdapter.OnPokemonClickListener interface
    // this interface is used to monitor clicks on a pokemon, and transition to the next activity

    @Override
    public void onPokemonClick(PokemonModel poke) {
        Intent intent = new Intent(this, PokemonDetailActivity.class);
        intent.putExtra("pokemon", poke);
        startActivity(intent);
    }

    // Methods for getting random pokemon

    private PokemonListModel getRandomPokemon(int numPokemon, int lowerBoundDex, int upperBoundDex) {
        return PokemonListModel.getPokemonList(getDifferentRandomNums(numPokemon, lowerBoundDex, upperBoundDex));
    }

    private int[] getDifferentRandomNums(int count, int min, int max) {
        // there would be an infinite loop when trying to find different numbers
        // if the range is smaller than the count. Therefore count<=(max-min+1)
        count = Math.min(count, max-min+1);
        Set<Integer> numSet = new HashSet<Integer>();
        int[] nums = new int[count];
        for(int i=0; i<count; i++) {
            nums[i] = getRandomNumNotInSet(min, max, numSet);
            numSet.add(nums[i]);
        }
        return nums;
    }

    private int getRandomNumNotInSet(int min, int max, Set nums) {
        while(true) {
            int num = (int) ((max-min+1)*Math.random() + min);
            if(!nums.contains(num)) {
                return num;
            }
        }
    }
}
