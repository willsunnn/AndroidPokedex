package com.example.pokedexapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class PokemonListActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,  PokemonListAdapter.OnPokemonClickListener {

    PokemonListModel pokemon;
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
        pokemon = getRandomPokemon();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.random_team_item:
                pokemon = getRandomPokemon();
                adapter = new PokemonListAdapter(pokemon, this);
                recyclerView.setAdapter(adapter);
                return true;
            case R.id.hello_pokemon_item:
                PokemonModel poke = this.pokemon.getRandomPokemon();
                createAlertDialog(poke);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createAlertDialog(PokemonModel poke) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.pokemon_alert_dialog, null);
        builder.setView(view);
        builder.show();
        ImageView image = view.findViewById(R.id.alert_dialog_image);
        TextView text = view.findViewById(R.id.alert_dialog_text);
        Picasso.get().load(poke.getFrontSprite()).into(image);
        text.setText("Hello "+poke.getName()+"!");
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

    private PokemonListModel getRandomPokemon() {
        return getRandomPokemon(7,1,151);
    }

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
