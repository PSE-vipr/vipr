package edu.kit.ipd.pp.prolog.vipr.controller.toolbarlistener;

import java.awt.event.ActionEvent;

import edu.kit.ipd.pp.prolog.vipr.controller.AbstractListener;
import edu.kit.ipd.pp.prolog.vipr.controller.Calculation;
import edu.kit.ipd.pp.prolog.vipr.controller.CalculationManager;

/**
 * Dieser Listener ermöglicht das Pausieren einer NextSolution- bzw.
 * AllSolutions-Berechnung.
 */
public class ButtonPauseListener implements AbstractListener {

    /**
     * Die aktuelle Berechnung.
     */
    private Calculation calculation;

    /**
     * Erzeugt den Listener.
     */
    public ButtonPauseListener() {
        CalculationManager.getInstance().addListener(this);
    }

    /**
     * Setzt die Pause-Variable in Calculation auf pausiert. Jegliche Berechnungen
     * werden daraufhin am Ende des Berechnungsschrittes aufhören.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (calculation != null) {
            calculation.setPause();
        }
    }

    @Override
    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

}
