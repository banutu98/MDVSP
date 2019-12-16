package business;

import data.dao.jpa.AssociationDAOJpa;
import data.dao.models.Association;
import data.dao.models.Depot;
import data.dao.models.Trip;
import data.dao.spec.AssociationDAO;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TabuSolver implements SolverStrategy {

    @EJB
    private AssociationDAO associationDAO;

    private double getFitness(List<Pair<Trip, Depot>> candidate, double[][] costGraph) {
        double cost = 0;
        for (int i = 0; i < candidate.size() - 1; i++) {
            int firstId = candidate.get(i).getKey().getId(), secondId = candidate.get(i + 1).getKey().getId();
            cost += costGraph[firstId][secondId];
        }
        return cost;
    }

    private List<List<Pair<Trip, Depot>>> getNeighbours(List<Pair<Trip, Depot>> candidate) {
        List<List<Pair<Trip, Depot>>> result = new ArrayList<>();
        permute(0, candidate, result);
        return result;
    }

    private void permute(int start, List<Pair<Trip, Depot>> candidate, List<List<Pair<Trip, Depot>>> result) {
        if (start == candidate.size() - 1) {
            ArrayList<Pair<Trip, Depot>> list = new ArrayList<>(candidate);
            result.add(list);
            return;
        }
        for (int i = start; i < candidate.size(); i++) {
            swap(candidate, i, start);
            permute(start + 1, candidate, result);
            swap(candidate, i, start);
        }
    }

    private void swap(List<Pair<Trip, Depot>> candidate, int i, int j) {
        Pair<Trip, Depot> temp = candidate.get(i);
        candidate.set(i, candidate.get(j));
        candidate.set(j, temp);
    }

    private double getTripCost(Association assoc1, Association assoc2) {
        return Math.sqrt(Math.pow(assoc1.getEndLocation().getX() - assoc2.getStartLocation().getX(), 2) +
                Math.pow(assoc1.getEndLocation().getY() - assoc2.getStartLocation().getY(), 2));
    }

    private boolean isValidTrip(Trip trip1, Trip trip2) {
        return trip2.getEndTime().isAfter(trip1.getEndTime());
    }

    private boolean isValidCandidate(List<Pair<Trip, Depot>> candidate) {
        for (int i = 0; i < candidate.size() - 1; i++)
            for (int j = i + 1; j < candidate.size(); j++) {
                if (!(candidate.get(j).getKey().getStartTime().isAfter(candidate.get(i).getKey().getEndTime())))
                    return false;
            }
        for (int i = 0; i < candidate.size() - 1; i++) {
            if (candidate.get(i).getValue().getCapacity() == 0 && candidate.get(i + 1).getValue().getCapacity() > 0)
                return false;
        }
        return true;
    }

    private double[][] initGraph() {
        List<Association> associations = associationDAO.readAll();
        double[][] graph = new double[associations.size() + 1][associations.size() + 1];
        for (int i = 0; i < associations.size(); i++)
            for (int j = 0; j < associations.size(); j++) {
                if (i == j)
                    graph[i][j] = -1;
                else {
                    Association assoc1 = associations.get(i), assoc2 = associations.get(j);
                    if (isValidTrip(assoc1.getTrip(), assoc2.getTrip())) {
                        double cost = getTripCost(assoc1, assoc2);
                        graph[i][j] = cost;
                    } else
                        graph[i][j] = -1;
                }
            }
        return graph;
    }

    @Override
    public List<Pair<Trip, Depot>> solve() {
        SolverStrategy solver = new GreedySolver();
        List<Pair<Trip, Depot>> s0 = solver.solve();
        List<Pair<Trip, Depot>> sBest = s0;
        List<Pair<Trip, Depot>> bestCandidate = s0;
        LinkedList<List<Pair<Trip, Depot>>> tabuList = new LinkedList<>();
        tabuList.push(s0);
        double[][] costGraph = initGraph();
        int iterations = 5, maxTabuSize = 5;
        while (iterations > 0) {
            List<List<Pair<Trip, Depot>>> sNeighborhood = getNeighbours(bestCandidate);
            for (List<Pair<Trip, Depot>> sCandidate : sNeighborhood) {
                if (isValidCandidate(sCandidate) && !tabuList.contains(sCandidate) &&
                        getFitness(sCandidate, costGraph) > getFitness(bestCandidate, costGraph)) {
                    bestCandidate = sCandidate;
                }
            }
            if (getFitness(bestCandidate, costGraph) > getFitness(sBest, costGraph))
                sBest = bestCandidate;
            tabuList.push(bestCandidate);
            if (tabuList.size() > maxTabuSize)
                tabuList.removeFirst();
            iterations--;
        }
        return sBest;
    }
}
