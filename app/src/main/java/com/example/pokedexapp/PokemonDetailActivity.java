package com.example.pokedexapp;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PokemonDetailActivity extends AppCompatActivity {
    PokemonModel pokemon;

    ImageView frontSprite;
    ImageView backSprite;

    TextView nameView;
    TextView numberView;
    TextView weightView;
    TextView heightView;
    TextView typeView;

    LinearLayout movesView;
    LinearLayout abilitiesView;

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

        movesView = findViewById(R.id.MoveView);
        abilitiesView = findViewById(R.id.AbilityView);

        updateViewFromModel();
    }

    private void updateViewFromModel() {
        frontSprite.setImageResource(pokemon.getFrontSprite());
        backSprite.setImageResource(pokemon.getBackSprite());

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
        for(PokemonMove move: moves) {
            TextView moveView = new TextView(this);
            moveView.setText(move.getMoveName()+": "+move.getMoveDescription());
            movesView.addView(moveView);
        }
    }

    private void updateAbilitiesFromModel() {
        PokemonAbility[] abilities = pokemon.getAbilities();
        for(PokemonAbility ability: abilities) {
            TextView abilityView = new TextView(this);
            abilityView.setText(ability.getAbilityName()+": "+ability.getAbilityDescription());
            abilitiesView.addView(abilityView);
        }

    }
}
