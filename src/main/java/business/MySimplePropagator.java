package business;

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;

import java.util.ArrayList;

public class MySimplePropagator extends Propagator<IntVar> {
    private IntVar x, y;
    private ArrayList<int[]> assoc;
    public MySimplePropagator(IntVar x, IntVar y, ArrayList<int[]> assoc) {
        super(x,y);
        this.x = x;
        this.y = y;
        this.assoc = assoc;
    }
    @Override
    public void propagate(int evtmask) throws ContradictionException {
        x.updateLowerBound(x.getLB(), this);
        y.updateUpperBound(y.getUB(), this);
    }
    @Override
    public ESat isEntailed() {
        for (int[] ints : assoc)
            if (ints[0] == x.getValue() && ints[1] == y.getValue())
                return ESat.TRUE;
        return ESat.FALSE;
    }
}
