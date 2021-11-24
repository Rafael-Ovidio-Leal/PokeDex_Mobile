package br.com.up.pokedexmobile.connection;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.up.pokedexmobile.models.Details;
import br.com.up.pokedexmobile.models.Pokemon;

public class PokeAPI {

    public void getPokemons(OnPokeAPIListener listener){

        PokeConnectionAsyncTask asyncTask = new PokeConnectionAsyncTask(
                new PokeConnectionAsyncTask.OnRequestListener() {
                    @Override
                    public void onRequestFinish(JSONObject object){

                        try{

                            ArrayList<Pokemon> pokemons = new ArrayList<>();

                            JSONArray results = object.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++){

                                JSONObject pokeObject = results.getJSONObject(i);
                                int id = i + 1;
                                pokemons.add(new Pokemon(id, pokeObject.getString("name"), "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + id + ".png"));
                            }

                            listener.onFinish(pokemons);

                        }catch (Exception e){
                            listener.onFinish(null);
                        }

                    }
                }
        );
        //151 em game no total são 891
        asyncTask.execute("https://pokeapi.co/api/v2/pokemon?limit=151");
    }

    public void getDetails(int id, OnPokeAPIDetailListener listener) {
        PokeConnectionAsyncTask asyncTask = new PokeConnectionAsyncTask(
                new PokeConnectionAsyncTask.OnRequestListener() {
                    @Override
                    public void onRequestFinish(JSONObject object){

                        try{
                            //Instâncias
                            ArrayList<String> pokeAbilities = new ArrayList<>();
                            ArrayList<String> pokeMoves = new ArrayList<>();
                            ArrayList<String> pokeStats = new ArrayList<>();
                            ArrayList<String> pokeTypes = new ArrayList<>();

                            Details details = new Details();

                            //get API
                            JSONArray abilities = object.getJSONArray("abilities");
                            JSONArray types = object.getJSONArray("types");
                            JSONArray moves = object.getJSONArray("moves");
                            JSONArray stats = object.getJSONArray("stats");


                            for (int i = 0; i < abilities.length(); i++){
                                JSONObject abilitiesObject = abilities.getJSONObject(i);
                                JSONObject abilityObject = abilitiesObject.getJSONObject("ability");

                                String nameAbility = abilityObject.getString("name");

                                pokeAbilities.add(nameAbility);
                            }

                            for (int i = 0; i < types.length(); i++){
                                JSONObject typesObject = types.getJSONObject(i);
                                JSONObject typeObject = typesObject.getJSONObject("type");

                                String nameType = typeObject.getString("name");

                                pokeTypes.add(nameType);
                            }

                            for (int i = 0; i < moves.length(); i++){
                                JSONObject movesObject = moves.getJSONObject(i);
                                JSONObject moveObject = movesObject.getJSONObject("move");

                                String nameMove = moveObject.getString("name");

                                pokeMoves.add(nameMove);
                            }

                            for (int i = 0; i < stats.length(); i++){
                                JSONObject statsObject = stats.getJSONObject(i);
                                int base = statsObject.getInt("base_stat");

                                JSONObject statObject = statsObject.getJSONObject("stat");
                                String nameStat = statObject.getString("name");

                                pokeStats.add(nameStat + ": " + base);
                            }

                            details.setAbilities(pokeAbilities);
                            details.setTypes(pokeTypes);
                            details.setMoves(pokeMoves);
                            details.setStats(pokeStats);
                            listener.onFinish(details);

                        }catch (Exception e){
                            listener.onFinish(null);
                        }
                    }
                }
        );
        //151 em game no total são 891
        asyncTask.execute("https://pokeapi.co/api/v2/pokemon/"+id+"/");
    }

    public interface OnPokeAPIListener{
        void onFinish(ArrayList<Pokemon> pokemons);
    }

    public interface OnPokeAPIDetailListener{
        void onFinish(Details details);
    }
}
