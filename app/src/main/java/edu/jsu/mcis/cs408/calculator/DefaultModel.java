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
        setState(MainActivity.CalculatorState.CLEAR);

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

    /* Model would need to call other methods from within
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

        if (oldState == MainActivity.CalculatorState.RESULT) {
            this.lhs = this.result;
        }

        switch (state) {
            case CLEAR:
                this.op = MainActivity.Operator.NONE;
                setLhs(new BigDecimal("0"));
                setRhs(new BigDecimal("0"));
                this.buffer = new StringBuilder();
                setResult(new BigDecimal("0"));
                break;
            case OP_SELECTED:
                this.rhs = this.lhs;
            case RESULT:
                if (this.op == MainActivity.Operator.DIVIDE && this.rhs.equals(0)) {
                    Log.i(TAG, "Cannot divide by Zero");
                    setResult(new BigDecimal("0"));
                    this.state = MainActivity.CalculatorState.ERROR;
                }
                else {
                    switch (this.op) {
                        case PLUS:
                            setResult(this.lhs.add(this.rhs));
                            break;
                        case MINUS:
                            setResult(this.lhs.subtract(this.rhs));
                            break;
                        case MULTIPLY:
                            setResult(this.lhs.multiply(this.rhs));
                            break;
                        case DIVIDE:
                            setResult(this.lhs.divide(this.rhs));
                            break;
                        default:
                            Log.i(TAG, "Problem calculating result");
                            this.state = MainActivity.CalculatorState.ERROR;
                            break;
                    }
                }
                break;
            default:
                Log.i(TAG, "Called setState on state other than CLEAR, OP_SELECTED, or RESULT");
                break;
        }

        //firePropertyChange(DefaultController.ELEMENT_STATE, oldState, newState);

    }

    public void setOp(MainActivity.Operator newOp) {

        switch (state) {
            case RESULT:
                this.lhs = this.result;
            case LHS: case CLEAR:

                if (newOp == MainActivity.Operator.NEGATE) {
                    BigDecimal negated = this.lhs.negate();
                    Log.i(TAG, "Negating LHS");
                    setLhs(negated);
                } else if (newOp == MainActivity.Operator.SQRT) {
                    double rooted = Math.sqrt(this.lhs.doubleValue());
                    Log.i(TAG, "Square Rooting LHS");
                    setLhs(new BigDecimal(rooted));
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
                    this.rhs = this.lhs;
                    BigDecimal negated = this.rhs.negate();
                    Log.i(TAG, "Negating RHS");
                    setRhs(negated);
                } else if (newOp == MainActivity.Operator.SQRT) {
                    this.rhs = this.lhs;
                    double rooted = Math.sqrt(this.rhs.doubleValue());
                    Log.i(TAG, "Square Rooting RHS");
                    setRhs(new BigDecimal(rooted));
                } else if (newOp == MainActivity.Operator.PERCENT) {
                    this.rhs = this.lhs;
                    BigDecimal divide = new BigDecimal("100");
                    BigDecimal percent = this.rhs.divide(divide);
                    BigDecimal newrhs = this.lhs.multiply(percent);
                    Log.i(TAG,"Percenting RHS");
                    setRhs(newrhs);
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
                    double rooted = Math.sqrt(this.rhs.doubleValue());
                    Log.i(TAG, "Square Rooting RHS");
                    setRhs(new BigDecimal(rooted));
                } else if (newOp == MainActivity.Operator.PERCENT) {
                    BigDecimal divide = new BigDecimal("100");
                    BigDecimal percent = this.rhs.divide(divide);
                    BigDecimal newrhs = this.lhs.multiply(percent);
                    Log.i(TAG,"Percenting RHS");
                    setRhs(newrhs);
                }
                else { //button is PLUS, MINUS, MULTIPLY, or DIVIDE
                    //call for result, set lhs to result, and set op !!!!!!!!!!!!!!!!!!!!!
                    setState(MainActivity.CalculatorState.RESULT);
                    if (this.state != MainActivity.CalculatorState.ERROR) {
                        this.lhs = this.result;
                        MainActivity.Operator oldOp = this.op;
                        this.op = newOp;
                        Log.i(TAG, "Operator Change: From " + oldOp + " to " + newOp);
                        this.state = MainActivity.CalculatorState.OP_SELECTED;
                    }
                }
                break;
            default: //error case, should not be able to click
                Log.i(TAG,"Cannot choose an operator after getting error");

        }

    }

    public void setLhs(BigDecimal newLhs) {

        if (newLhs.toString().contains(".")) {
            if (newLhs.toString().length() <= 13) {
                BigDecimal oldLhs = this.lhs;
                this.lhs = newLhs;

                Log.i(TAG, "LHS Change: From " + oldLhs + " to " + newLhs);

                firePropertyChange(DefaultController.ELEMENT_LHS, oldLhs, newLhs);
            }
        }
        else if (newLhs.toString().length() <= 12) {
            BigDecimal oldLhs = this.lhs;
            this.lhs = newLhs;

            Log.i(TAG, "LHS Change: From " + oldLhs + " to " + newLhs);

            firePropertyChange(DefaultController.ELEMENT_LHS, oldLhs, newLhs);
        }
        else {
            Log.i(TAG, "Cannot enter LHS with more than 12 digits and a dot");
        }

    }

    public void setRhs(BigDecimal newRhs) {

        if (newRhs.toString().contains(".")) {
            if (newRhs.toString().length() <= 13) {
                BigDecimal oldRhs = this.rhs;
                this.rhs = newRhs;

                Log.i(TAG, "RHS Change: From " + oldRhs + " to " + newRhs);

                firePropertyChange(DefaultController.ELEMENT_RHS, oldRhs, newRhs);
            }
        }
        else if (newRhs.toString().length() <= 12) {
            BigDecimal oldRhs = this.rhs;
            this.rhs = newRhs;

            Log.i(TAG, "RHS Change: From " + oldRhs + " to " + newRhs);

            firePropertyChange(DefaultController.ELEMENT_RHS, oldRhs, newRhs);
        }
        else {
            Log.i(TAG, "Cannot enter RHS with more than 12 digits and a dot");
        }

    }

    public void setBuffer(StringBuilder newDigit) {

        switch (this.state) {
            case CLEAR: case ERROR: case RESULT:
                Log.i(TAG, "Starting fresh");
                this.state = MainActivity.CalculatorState.LHS;
                this.lhs = new BigDecimal("0");
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
            default:
                Log.i(TAG, "Setting Buffer on State Error");
        }
        
        //firePropertyChange(DefaultController.ELEMENT_BUFFER, oldDigit, newDigit);

    }

    public void setResult(BigDecimal newResult) {

        if (newResult.toString().contains(".")) {
            if (newResult.toString().length() <= 13) {
                BigDecimal oldResult = this.result;
                this.result = newResult;

                Log.i(TAG, "Result Change: From " + oldResult + " to " + newResult);

                firePropertyChange(DefaultController.ELEMENT_RESULT, oldResult, newResult);
            }
        }
        else if (newResult.toString().length() <= 12) {
            BigDecimal oldResult = this.result;
            this.result = newResult;

            Log.i(TAG, "Result Change: From " + oldResult + " to " + newResult);

            firePropertyChange(DefaultController.ELEMENT_RESULT, oldResult, newResult);
        }
        else {
            Log.i(TAG, "Cannot enter Result with more than 12 digits and a dot");
        }

    }
}
