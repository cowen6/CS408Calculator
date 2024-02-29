package edu.jsu.mcis.cs408.calculator;

import android.util.Log;

import java.math.BigDecimal;

public class DefaultModel extends AbstractModel {

    public static final String TAG = "DefaultModel";

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
    private StringBuilder buffer; //buffer for any new digits
    private BigDecimal result;


    /*
     * Initialize the model elements to either known default values, or restore
     * from a previous state.  We use the setter methods instead of changing the
     * values directly so that these changes are properly announced to the
     * Controller, and so that the Views can be updated accordingly.
     */

    //
    public void initDefault() {

        //Default
        setState(MainActivity.CalculatorState.CLEAR);
        setOp(MainActivity.Operator.NONE);
        setLhs(new BigDecimal("0"));
        setRhs(new BigDecimal("0"));
        setBuffer(new StringBuilder());
        setResult(new BigDecimal("0"));

    }

    /*
     * Simple getter methods
     */

    public MainActivity.CalculatorState getState() { return state; }
    public MainActivity.Operator getOp() { return op; }
    public BigDecimal getLhs() { return lhs; }
    public BigDecimal getRhs() { return rhs; }
    public StringBuilder getBuffer() { return buffer; }
    public BigDecimal getResult() { return result; }

    /*
     * Property Setters. While they all change their values, only the result and
     * LHS and RHS fire PropertyChange events, as they are the only properties
     * connected to physical elements in the views. Any registered AbstractController
     * subclasses will receive this event, and will propagate it to all registered
     * Views so that they can update themselves accordingly.
     */

    /* Implement algorithm to determine action here or in activity
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

    public void setState(MainActivity.CalculatorState newState) {

        MainActivity.CalculatorState oldState = this.state;
        this.state = newState;

        Log.i(TAG, "Calculator State Change: From " + oldState + " to " + newState);

        switch (state) {
            case CLEAR:
                this.op = MainActivity.Operator.NONE;
                setLhs(new BigDecimal("0"));
                this.rhs = new BigDecimal("0");
                this.buffer = new StringBuilder();
                this.result = new BigDecimal("0");
                break;
            case RESULT:
                //calculate result
                break;
            default:
                Log.i(TAG, "Called setState on state other than CLEAR or RESULT");
                break;
        }

        //firePropertyChange(DefaultController.ELEMENT_STATE, oldState, newState);

    }

    public void setOp(MainActivity.Operator newOp) {

        switch (state) {
            case LHS: case CLEAR: case RESULT:

                if (newOp == MainActivity.Operator.NEGATE) {
                    BigDecimal negated = this.lhs.negate();
                    Log.i(TAG, "Negating LHS");
                    setLhs(negated);
                } else if (newOp == MainActivity.Operator.SQRT) {
                    //BigDecimal rooted = this.lhs;
                    Log.i(TAG, "Square Rooting LHS");
                    //setLhs(rooted);
                } else if (newOp == MainActivity.Operator.PERCENT) {
                    Log.i(TAG, "Cannot find percent without second number");
                }
                else { //button is PLUS, MINUS, MULTIPLY, or DIVIDE
                    MainActivity.Operator oldOp = this.op;
                    this.op = newOp;
                    Log.i(TAG, "Operator Change: From " + oldOp + " to " + newOp);
                    this.state = MainActivity.CalculatorState.OP_SELECTED;
                }
                break;
            case OP_SELECTED:
                //RHS changes to number even after negate, sqrt or percent
                if (newOp == MainActivity.Operator.NEGATE) {
                    BigDecimal negated = this.rhs.negate();
                    Log.i(TAG, "Negating RHS");
                    setRhs(negated);
                } else if (newOp == MainActivity.Operator.SQRT) {
                    //BigDecimal rooted = this.rhs;
                    Log.i(TAG, "Square Rooting RHS");
                    //setRhs(rooted);
                } else if (newOp == MainActivity.Operator.PERCENT) {
                    BigDecimal percent = this.rhs.divide(this.lhs);
                    Log.i(TAG,"Percenting RHS");
                    setRhs(percent);
                }
                else { //button is PLUS, MINUS, MULTIPLY, or DIVIDE
                    MainActivity.Operator oldOp = this.op;
                    this.op = newOp;
                    Log.i(TAG, "Operator Change: From " + oldOp + " to " + newOp);
                }
                break;
            case RHS:

                if (newOp == MainActivity.Operator.NEGATE) {
                    BigDecimal negated = this.rhs.negate();
                    Log.i(TAG, "Negating RHS");
                    setRhs(negated);
                } else if (newOp == MainActivity.Operator.SQRT) {
                    //BigDecimal rooted = this.rhs;
                    Log.i(TAG, "Square Rooting RHS");
                    //setRhs(rooted);
                } else if (newOp == MainActivity.Operator.PERCENT) {
                    BigDecimal percent = this.rhs.divide(this.lhs);
                    Log.i(TAG,"Percenting RHS");
                    setRhs(percent);
                }
                else { //button is PLUS, MINUS, MULTIPLY, or DIVIDE
                    //call for result, set lhs to result, and set op !!!!!!!!!!!!!!!!!!!!!
                    MainActivity.Operator oldOp = this.op;
                    this.op = newOp;
                    Log.i(TAG, "Operator Change: From " + oldOp + " to " + newOp);
                    this.state = MainActivity.CalculatorState.OP_SELECTED;
                }
                break;
            default: //error case, should not be able to click
                Log.i(TAG,"Cannot choose an operator after getting error");

        }

    }

    public void setLhs(BigDecimal newLhs) {

        //Cannot be longer than what would fit on the screen (~12 char?)

        BigDecimal oldLhs = this.lhs;
        this.lhs = newLhs;

        Log.i(TAG, "LHS Change: From " + oldLhs + " to " + newLhs);

        firePropertyChange(DefaultController.ELEMENT_LHS, oldLhs, newLhs);

    }

    public void setRhs(BigDecimal newRhs) {

        //Cannot be longer than what would fit on the screen (~12 char?)

        BigDecimal oldRhs = this.rhs;
        this.rhs = newRhs;

        Log.i(TAG, "RHS Change: From " + oldRhs + " to " + newRhs);

        firePropertyChange(DefaultController.ELEMENT_RHS, oldRhs, newRhs);

    }

    public void setBuffer(StringBuilder newDigit) {

        switch (this.state) {
            case CLEAR: case ERROR: case RESULT:
                this.state = MainActivity.CalculatorState.LHS;
            case LHS:
                StringBuilder oldlhs = new StringBuilder(this.lhs.toString());
                StringBuilder newlhs = new StringBuilder(oldlhs);

                if (newDigit.toString() == ".") { //if adding dot
                    boolean dot = oldlhs.toString().contains(".");

                    if (dot) {//check if oldlhs already has dot
                        Log.i(TAG, "Cannot add multiple dots");
                    }
                    else {//lhs doesn't have a dot yet
                        newlhs.append(newDigit);
                        setLhs(new BigDecimal(newlhs.toString()));
                    }
                }
                else { //adding number
                    newlhs.append(newDigit);
                    setLhs(new BigDecimal(newlhs.toString()));
                }

                break;
            case OP_SELECTED:
                this.state = MainActivity.CalculatorState.RHS;
                this.rhs = new BigDecimal("0"); //resets in case of negate, sqrt, or percent
            case RHS:
                StringBuilder oldrhs = new StringBuilder(this.rhs.toString());
                StringBuilder newrhs = new StringBuilder(oldrhs);

                if (newDigit.toString() == ".") { //if adding dot
                    boolean dot = oldrhs.toString().contains(".");

                    if (dot) {//check if oldrhs already has dot
                        Log.i(TAG, "Cannot add multiple dots");
                    }
                    else {//lhs doesn't have a dot yet
                        newrhs.append(newDigit);
                        setRhs(new BigDecimal(newrhs.toString()));
                    }
                }
                else { //adding number
                    newrhs.append(newDigit);
                    setRhs(new BigDecimal(newrhs.toString()));
                }
                break;
        }
        
        //firePropertyChange(DefaultController.ELEMENT_BUFFER, oldDigit, newDigit);

    }

    public void setResult(BigDecimal newResult) {

        BigDecimal oldResult = this.result;
        this.result = newResult;

        Log.i(TAG, "Result Change: From " + oldResult + " to " + newResult);

        firePropertyChange(DefaultController.ELEMENT_RESULT, oldResult, newResult);

    }
}
