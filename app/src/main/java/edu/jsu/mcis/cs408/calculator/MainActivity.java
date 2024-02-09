package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private final int KEYS_HEIGHT = 4;
    private final int KEYS_WIDTH = 5;
    private String[] tags;
    private String[] text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Get StringArrays for button tags and text
        tags = getResources().getStringArray(R.array.tags);
        text = getResources().getStringArray(R.array.text);

        initLayout();



    }

    //Creates and shows a toast with the tag of the button pressed
    @Override
    public void onClick(View view) {
        Toast.makeText(binding.getRoot().getContext(), view.getTag().toString(), Toast.LENGTH_SHORT).show();
    }

    private void initLayout() {
        ConstraintLayout layout = binding.layout;
        /*  !!!!!!!!!!  LOOK UP HELP FOR ARRAYS OF ARRAYS   !!!!!!!!!!  */
        int[][] horizontals = new int[KEYS_HEIGHT][KEYS_WIDTH];
        int[][] verticals = new int[KEYS_WIDTH][KEYS_HEIGHT];

        //Get ids for all guidelines
        int north = binding.guideNorth.getId();
        int east = binding.guideEast.getId();
        int south = binding.guideSouth.getId();
        int west = binding.guideWest.getId();

        //Create the textOutput TextView
        int textid = View.generateViewId();
        TextView textOutput = new TextView(this);
        textOutput.setId(textid);
        textOutput.setTag("textOutput");
        textOutput.setText(getResources().getString(R.string.zero_start));
        textOutput.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        textOutput.setTextSize(48);
        layout.addView(textOutput);

        //Set textOutput's layout parameters
        LayoutParams txtparams = textOutput.getLayoutParams();
        txtparams.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
        txtparams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        textOutput.setLayoutParams(txtparams);

        //Create all Buttons and Add ids to arrays for constraints
        int btnnum = 0;
        for (int i = 0; i < KEYS_HEIGHT; ++i) {
            for (int j = 0; j < KEYS_WIDTH; ++j) {
                int id = View.generateViewId();
                Button btn = new Button(this);
                btn.setId(id);
                btn.setTag(tags[btnnum]);
                btn.setText(text[btnnum]);
                btn.setTextSize(24);
                btn.setOnClickListener(this);
                layout.addView(btn);
                horizontals[i][j] = id;
                verticals[j][i] = id;

               //Set button layout parameters
                LayoutParams params = btn.getLayoutParams();
                params.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
                params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
                btn.setLayoutParams(params);

                btnnum++;
            }
        }

        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        //Set constraints for textOutput
        set.connect(textid, ConstraintSet.LEFT, west, ConstraintSet.RIGHT);
        set.connect(textid, ConstraintSet.RIGHT, east, ConstraintSet.LEFT);
        set.connect(textid, ConstraintSet.TOP, north, ConstraintSet.BOTTOM);


        //Set constraints for Buttons
        for (int[] id : horizontals) {
            set.createHorizontalChain(west, ConstraintSet.RIGHT, east, ConstraintSet.LEFT, id, null, ConstraintSet.CHAIN_PACKED);
        }
        for (int[] id : verticals) {
            set.createVerticalChain(textid, ConstraintSet.BOTTOM, south, ConstraintSet.TOP, id, null, ConstraintSet.CHAIN_PACKED);
        }

        set.applyTo(layout);

    }
}