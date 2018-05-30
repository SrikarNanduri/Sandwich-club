package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAsTv;
    private TextView mAlsoKnownAsLabel;
    private TextView mOriginTv;
    private TextView mOriginLabel;
    private TextView mDescriptionTv;
    private TextView mDescriptionLable;
    private TextView mIngredientTv;
    private TextView mIngredientLable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTv = findViewById(R.id.also_known_tv);
        mAlsoKnownAsLabel = findViewById(R.id.alsoKnownAs_lable);
        mOriginTv = findViewById(R.id.origin_tv);
        mOriginLabel = findViewById(R.id.placeOfOrigin_lable);
        mDescriptionTv = findViewById(R.id.description_tv);
        mDescriptionLable = findViewById(R.id.description_lable);
        mIngredientTv = findViewById(R.id.ingredients_tv);
        mIngredientLable = findViewById(R.id.ingredients_lable);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // set Text to Also Known As
        if(sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            StringBuilder alsoKnownAsStringBuilder = new StringBuilder();
            //appends the first  item in the list
            alsoKnownAsStringBuilder.append(sandwich.getAlsoKnownAs().get(0));

            // if there are more this loop will append them to the first one with a , in between
            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownAsStringBuilder.append(", ");
                alsoKnownAsStringBuilder.append(sandwich.getAlsoKnownAs().get(i));
            }
            mAlsoKnownAsTv.setText(alsoKnownAsStringBuilder.toString());
        } else {
            // If the data is not present then hide the Text Views
            mAlsoKnownAsTv.setVisibility(View.GONE);
            mAlsoKnownAsLabel.setVisibility(View.GONE);
        }

        // set Text to Place Of Origin
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            // If the data is not present then hide the Text Views
            mOriginTv.setVisibility(View.GONE);
            mOriginLabel.setVisibility(View.GONE);
        } else {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        // set Text to Description
        if(sandwich.getDescription().isEmpty()){
            mDescriptionTv.setVisibility(View.GONE);
            mDescriptionLable.setVisibility(View.GONE);
        } else {
            mDescriptionTv.setText(sandwich.getDescription());
        }

        // set Text to Ingredients
        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            StringBuilder ingredientsStringBuilder = new StringBuilder();
            ingredientsStringBuilder.append("\u2022 ");
            //appends the first  item in the list
            ingredientsStringBuilder.append(sandwich.getIngredients().get(0));

            // if there are more this loop will append the to the first one
            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                ingredientsStringBuilder.append("\n");
                ingredientsStringBuilder.append("\u2022 ");
                ingredientsStringBuilder.append(sandwich.getIngredients().get(i));
            }
            mIngredientTv.setText(ingredientsStringBuilder.toString());
        } else {
            mIngredientTv.setVisibility(View.GONE);
            mIngredientLable.setVisibility(View.GONE);
        }
    }
}
