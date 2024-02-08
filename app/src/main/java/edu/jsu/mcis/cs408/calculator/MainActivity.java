package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final int NUM_BUTTONS = 20;
    private final int KEYS_HEIGHT = 4;
    private final int KEYS_WIDTH = 5;
    private String[] tags = getResources().getStringArray(R.array.tags);
    private String[] text = getResources().getStringArray(R.array.text);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initLayout();

        //Add toast dialog of the button's tag when clicked?
    }

    private void initLayout() {
        ConstraintLayout layout = binding.layout;
        //LOOK UP HELP FOR ARRAYS OF ARRAYS
        int[][] horizontals = new int[KEYS_HEIGHT][KEYS_WIDTH];
        int[][] verticals = new int[KEYS_WIDTH][KEYS_HEIGHT];

        //Create the Output TextView !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //Use double for loop for arrays????? (Would need to rearrange string arrays)
        for (int i = 0; i < NUM_BUTTONS; ++i) {
            int id = View.generateViewId();
            Button btn = new Button(this);
            btn.setId(id);
            btn.setTag(tags[i]);
            btn.setText(text[i]);
            btn.setTextSize(24);
            layout.addView(btn);

            //Add buttons to arrays for layout constraints !!!!!!!!

            LayoutParams params = btn.getLayoutParams();
            params.width = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            params.height = ConstraintLayout.LayoutParams.MATCH_CONSTRAINT;
            btn.setLayoutParams(params);
        }

        //Add buttons to chains for layout !!!!!!!!!!!!!!!!!!!!!!!

    }
}