package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject JsonObject = new JSONObject(json);

            JSONObject name = JsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");

            JSONArray JsonArrayAlsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = convertToList(JsonArrayAlsoKnownAs);

            String placeOfOrigin = JsonObject.getString("placeOfOrigin");

            String description = JsonObject.getString("description");

            String image = JsonObject.getString("image");

            JSONArray JsonArrayIngredients = JsonObject.getJSONArray("ingredients");
            List<String> ingredients = convertToList(JsonArrayIngredients);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> convertToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
