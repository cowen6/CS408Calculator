package edu.jsu.mcis.cs408.calculator;

import android.util.Log;

import java.math.BigDecimal;

public class DefaultModel extends AbstractModel {

    public static final String TAG = "DefaultModel";

    /*
     * This is a simple implementation of an AbstractModel which encapsulates
     * two text fields, text1 and text2, which (in this example) are each
     * reflected in the View as an EditText field and a TextView label.
     */

    private String text1;
    private String text2;

    /*
     * This is a simple implementation of an AbstractModel which encapsulates
     * two number fields(lhs and rhs), a calculator state property(state),
     * an operator property(op), and a string buffer which (in this example)
     * are reflected in the View as BigDecimals, state enums, or a StringBuilder.
     */

    private MainActivity.CalculatorState state;
    private BigDecimal lhs;
    private BigDecimal rhs;
    private MainActivity.Operator op;
    // Do I need to include string builder here or within methods?
    private StringBuilder buffer; //newdigit????


    /*
     * Initialize the model elements to either known default values, or restore
     * from a previous state.  We use the setter methods instead of changing the
     * values directly so that these changes are properly announced to the
     * Controller, and so that the Views can be updated accordingly.
     */

    //
    public void initDefault() {

        setText1("Sample Text 1");
        setText2("Sample Text 2");

    }

    // !!!!!!!!!! CHANGE/ADD METHODS TO REFLECT NEW VARIABLE/CONSTANTS !!!!!!!!!!

    /*
     * Simple getter methods
     */

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    /*
     * Setters for text1 and text2.  Notice that, in addition to changing the
     * values, these methods announce the change to the controller by firing a
     * PropertyChange event.  Any registered AbstractController subclasses will
     * receive this event, and will propagate it to all registered Views so that
     * they can update themselves accordingly.
     */

    public void setText1(String newText) {

        String oldText = this.text1;
        this.text1 = newText;

        Log.i(TAG, "Text1 Change: From " + oldText + " to " + newText);

        firePropertyChange(DefaultController.ELEMENT_TEXT1_PROPERTY, oldText, newText);

    }

    public void setText2(String newText) {

        String oldText = this.text2;
        this.text2 = newText;

        Log.i(TAG, "Text2 Change: From " + oldText + " to " + newText);

        firePropertyChange(DefaultController.ELEMENT_TEXT2_PROPERTY, oldText, newText);

    }

}
