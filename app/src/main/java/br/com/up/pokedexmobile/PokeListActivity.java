package br.com.up.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import br.com.up.pokedexmobile.adapter.PokeAdapter;
import br.com.up.pokedexmobile.connection.PokeAPI;
import br.com.up.pokedexmobile.models.Pokemon;

public class PokeListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPokeList;
    private ArrayList<Pokemon> pokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_list);

        recyclerViewPokeList = findViewById(R.id.recycler_poke_list);
        recyclerViewPokeList.setLayoutManager(new GridLayoutManager(this, 3));

        recyclerViewPokeList.setAdapter(new PokeAdapter(pokemons, new PokeAdapter.OnPokemonClickListener() {
            @Override
            public void onPokemonClick(Pokemon pokemon) {

                Intent intent = new Intent(PokeListActivity.this,PokeDetailsActivity.class);

                intent.putExtra("ID", pokemon.getId());
                intent.putExtra("Nome", pokemon.getNome());
                intent.putExtra("Image", pokemon.getImage());
                startActivity(intent);

            }
        }));

        new PokeAPI().getPokemons(new PokeAPI.OnPokeAPIListener() {
            @Override
            public void onFinish(ArrayList<Pokemon> pokemons) {
                PokeListActivity.this.pokemons.addAll(pokemons);
                recyclerViewPokeList.getAdapter().notifyDataSetChanged();
            }
        });

    }

}