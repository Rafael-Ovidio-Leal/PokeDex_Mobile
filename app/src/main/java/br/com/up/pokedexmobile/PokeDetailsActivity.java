package br.com.up.pokedexmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.up.pokedexmobile.connection.PokeAPI;
import br.com.up.pokedexmobile.models.Details;

import android.graphics.Color;

public class PokeDetailsActivity extends AppCompatActivity {

    private int id;
    private String nome;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_details);

        View background = findViewById(R.id.background);
        ImageView poke_image = findViewById(R.id.poke_image);
        TextView poke_details = findViewById(R.id.poke_details);
        TextView detail_type = findViewById(R.id.detail_type);
        TextView detail_stats = findViewById(R.id.detail_stats);
        TextView detail_abilities = findViewById(R.id.detail_abilitie);
        TextView detail_move = findViewById(R.id.detail_move);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 1);
        nome = intent.getStringExtra("Nome");
        image = intent.getStringExtra("Image");

        new PokeAPI().getDetails(id, new PokeAPI.OnPokeAPIDetailListener() {
            @Override
            public void onFinish(Details details) {

                //set nome e image
                Picasso.get().load(image).into(poke_image);
                poke_details.setText(nome);

                //set details
                String types = "";
                String stats = "";
                String abilities = "";
                String moves = "";

                int color = getColorByType(details.getTypes().get(0));
                background.setBackgroundColor(color);


                for (int i = 0; i < details.getTypes().size(); i++) {
                    types += details.getTypes().get(i) + "\n";
                }

                for (int i = 0; i < details.getStats().size(); i++) {
                    stats += details.getStats().get(i) + "\n";
                }

                for (int i = 0; i < details.getAbilities().size(); i++) {
                    abilities += details.getAbilities().get(i) + "\n";
                }

                for (int i = 0; i < details.getMoves().size(); i++) {
                    moves += details.getMoves().get(i) + "\n";
                }

                detail_type.setText(types);
                detail_stats.setText(stats);
                detail_abilities.setText(abilities);
                detail_move.setText(moves);

            }
        });

    }


    public static int getColorByType(String type) {
        switch(type)
        {

            case "normal":
                return Color.parseColor("#A4A27A");


            case "dragon":
                return Color.parseColor("#743BFB");



            case "psychic":
                return Color.parseColor("#F15B85");


            case "electric":
                return Color.parseColor("#E9CA3C");


            case "ground":
                return Color.parseColor("#D9BF6C");


            case "grass":
                return Color.parseColor("#81C85B");

            case "poison":
                return Color.parseColor("#A441A3");

            case "steel":
                return Color.parseColor("#BAB7D2");


            case "fairy":
                return Color.parseColor("#DDA2DF");


            case "fire":
                return Color.parseColor("#F48130");


            case "fight":
                return Color.parseColor("#BE3027");


            case "bug":
                return Color.parseColor("#A8B822");


            case "ghost":
                return Color.parseColor("#705693");


            case "dark":
                return Color.parseColor("#745945");


            case "ice":
                return Color.parseColor("#9BD8D8");


            case "water":
                return Color.parseColor("#658FF1");
            default:
                return Color.parseColor("#658FA0");
        }
    }
}
