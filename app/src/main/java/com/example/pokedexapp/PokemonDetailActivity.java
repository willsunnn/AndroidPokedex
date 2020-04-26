package com.example.pokedexapp;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

public class PokemonDetailActivity extends AppCompatActivity
        implements PokemonMove.MoveModelUpdateListener, PokemonAbility.AbilityModelUpdateListener {
    PokemonModel pokemon;

    ImageView frontSprite;
    ImageView backSprite;

    TextView nameView;
    TextView numberView;
    TextView weightView;
    TextView heightView;
    TextView typeView;

    LinearLayout movesParentView;
    LinearLayout abilitiesParentView;

    HashMap<Integer,TextView> movesView;
    HashMap<Integer,TextView> abilitiesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        pokemon = getIntent().getParcelableExtra("pokemon");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(pokemon.getName());

        frontSprite = findViewById(R.id.FrontSpriteView);
        backSprite = findViewById(R.id.BackSpriteView);

        nameView = findViewById(R.id.NameView);
        numberView = findViewById(R.id.NumberView);
        weightView = findViewById(R.id.WeightView);
        heightView = findViewById(R.id.HeightView);
        typeView = findViewById(R.id.TypeView);

        movesParentView = findViewById(R.id.MoveView);
        abilitiesParentView = findViewById(R.id.AbilityView);

        updateViewFromModel();
    }

    private void updateViewFromModel() {
        Picasso.get().load(pokemon.getFrontSprite()).into(frontSprite);
        Picasso.get().load(pokemon.getBackSprite()).into(backSprite);

        nameView.setText("Name: "+pokemon.getName());
        numberView.setText("Pokemon Number: "+Integer.toString(pokemon.getDexNumber()));
        weightView.setText("Weight: "+Integer.toString(pokemon.getWeight()));
        heightView.setText("Height: "+Integer.toString(pokemon.getHeight()));
        typeView.setText("Type: "+pokemon.getTypesAsString());

        updateMovesFromModel();
        updateAbilitiesFromModel();
    }

    private void updateMovesFromModel() {
        PokemonMove[] moves = pokemon.getMoves();
        movesView = new HashMap<Integer, TextView>();
        for(PokemonMove move: moves) {
            if(move.getMoveDescription().equals("")) {
                move.registerMoveModelUpdateListener(this);
                move.loadDescriptionFromApi();
            }
            TextView moveView = new TextView(this);
            moveView.setText(move.getMoveName()+": "+move.getMoveDescription());
            movesParentView.addView(moveView);
            movesView.put(move.getMoveId(), moveView);
        }
    }

    private void updateAbilitiesFromModel() {
        PokemonAbility[] abilities = pokemon.getAbilities();
        abilitiesView = new HashMap<Integer, TextView>();
        for(PokemonAbility ability: abilities) {
            if(ability.getAbilityDescription().equals("")) {
                ability.registerAbilityModelUpdateListener(this);
                ability.loadDescriptionFromApi();
            }
            TextView abilityView = new TextView(this);
            abilityView.setText(ability.getAbilityName()+": "+ability.getAbilityDescription());
            abilitiesParentView.addView(abilityView);
            abilitiesView.put(ability.getAbilityId(), abilityView);
        }

    }

    // Methods to handle asynchronous loading of data from the api

    @Override
    public void onMoveModelUpdate(PokemonMove move) {
        TextView moveView = movesView.get(move.getMoveId());
        moveView.setText(move.getMoveName()+": "+move.getMoveDescription());
        move.removeMoveModelUpdateListener();
    }

    @Override
    public void onAbilityModelUpdate(PokemonAbility ability) {
        TextView abilityView = abilitiesView.get(ability.getAbilityId());
        abilityView.setText(ability.getAbilityName()+": "+ability.getAbilityDescription());
        ability.removeAbilityModelUpdateListener();
    }
}
