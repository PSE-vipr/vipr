package edu.kit.ipd.pp.prolog.vipr;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import edu.kit.ipd.pp.prolog.vipr.model.ast.Cut;

public class CutTest {

    @Test
    public void CutGetTokensTest() {
        Cut cut = new Cut();
        assertEquals(new LinkedList<>(), cut.getTokens());
    }

    @Test
    public void CutDeepCopy() {
        Cut cut = new Cut();
        assertEquals(cut.getClass(), cut.deepCopy().getClass());
    }

}
