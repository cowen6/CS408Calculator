package edu.jsu.mcis.cs408.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.calculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AbstractView {

    public static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private final int KEYS_HEIGHT = 4;
    private final int KEYS_WIDTH = 5;
    private String[] tags;
    private String[] text;
    private DefaultController controller;

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

        /* Create Controller and Model */

        controller = new DefaultController();
        DefaultModel model = new DefaultModel();

        /* Register Activity View and Model with Controller */

        controller.addView(this);
        controller.addModel(model);

        /* Initialize Model to Default Values */

        model.initDefault();

    }

    @Override
    public void modelPropertyChange(final PropertyChangeEvent evt) {

        /*
         * This method is called by the "propertyChange()" method of AbstractController
         * when a change is made to an element of a Model.  It identifies the element that
         * was changed and updates the View accordingly.
         */

        String propertyName = evt.getPropertyName();
        String propertyValue = evt.getNewValue().toString();

        Log.i(TAG, "New " + propertyName + " Value from Model: " + propertyValue);

        if ( propertyName.equals(DefaultController.ELEMENT_LHS) ) {

            TextView output = binding.layout.findViewWithTag("textOutput");
            String oldPropertyValue = output.getText().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                output.setText(propertyValue);
            }

        }

        else if ( propertyName.equals(DefaultController.ELEMENT_RHS) ) {

            TextView output = binding.layout.findViewWithTag("textOutput");
            String oldPropertyValue = output.getText().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                output.setText(propertyValue);
            }

        }

        else if ( propertyName.equals(DefaultController.ELEMENT_RESULT) ) {

            TextView output = binding.layout.findViewWithTag("textOutput");
            String oldPropertyValue = output.getText().toString();

            if ( !oldPropertyValue.equals(propertyValue) ) {
                output.setText(propertyValue);
            }

        }


    }

    public enum CalculatorState {
        CLEAR, LHS, OP_SELECTED, RHS, RESULT, ERROR
    }

    public enum Operator {
        NONE, PLUS, MINUS, MULTIPLY, DIVIDE, NEGATE, SQRT, PERCENT
    }

    //Creates and shows a toast with the tag of the button pressed
    class CalculatorClickHandler implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String tag = view.getTag().toString();
            Toast toast = Toast.makeText(binding.getRoot().getContext(), tag, Toast.LENGTH_SHORT);
            toast.show();

            /* Implement algorithm to determine action here or in model
            *
            * Model would need to call other methods from within
            * (If number or dot, call change buffer to add digit
            *   add to either lhs or rhs
            *   (would check if dot is already contained if adding dot)
            * else if operator, call change operator
            *   may call equal if negate or sqrt
            *   may change rhs if percent (does nothing if lhs)
            * else if equal or clear, call change state
            *   clear resets calculator, clearing all elements via init default?
            *   equal calculates equation, calculates equation via result?)
            *
            * All operations depend on the calculator state and may change the state
            */

            StringBuilder digit;

            // INSERT EVENT HANDLING CODE HERE
            switch (tag) {
                //button is an operator button
                case "btnPlus":
                    controller.changeElementOP(Operator.PLUS);
                    break;
                case "btnMinus":
                    controller.changeElementOP(Operator.MINUS);
                    break;
                case "btnMultiply":
                    controller.changeElementOP(Operator.MULTIPLY);
                    break;
                case "btnDivide":
                    controller.changeElementOP(Operator.DIVIDE);
                    break;
                case "btnNegate":
                    controller.changeElementOP(Operator.NEGATE);
                    break;
                case "btnSqrt":
                    controller.changeElementOP(Operator.SQRT);
                    break;
                case "btnPercent":
                    controller.changeElementOP(Operator.PERCENT);
                    break;

                //button is either the clear or equal button
                case "btnClear":
                    controller.changeElementState(CalculatorState.CLEAR);
                    break;
                case "btnEqual":
                    controller.changeElementState(CalculatorState.RESULT);
                    break;

                //Button is a number button or the Dot button
                case "btn0":
                    digit = new StringBuilder("0");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn1":
                    digit = new StringBuilder("1");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn2":
                    digit = new StringBuilder("2");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn3":
                    digit = new StringBuilder("3");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn4":
                    digit = new StringBuilder("4");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn5":
                    digit = new StringBuilder("5");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn6":
                    digit = new StringBuilder("6");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn7":
                    digit = new StringBuilder("7");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn8":
                    digit = new StringBuilder("8");
                    controller.changeElementBuffer(digit);
                    break;
                case "btn9":
                    digit = new StringBuilder("9");
                    controller.changeElementBuffer(digit);
                    break;
                case "btnDot":
                    digit = new StringBuilder(".");
                    controller.changeElementBuffer(digit);
                    break;

            }
        }

    }

    private void initLayout() {
        ConstraintLayout layout = binding.layout;
        int[][] horizontals = new int[KEYS_HEIGHT][KEYS_WIDTH];
        int[][] verticals = new int[KEYS_WIDTH][KEYS_HEIGHT];
        CalculatorClickHandler click = new CalculatorClickHandler();

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
                btn.setOnClickListener(click);
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