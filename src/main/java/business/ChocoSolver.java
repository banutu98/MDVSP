package business;

import data.dao.jpa.CustomersDAOJpa;
import data.dao.jpa.DriversDAOJpa;
import data.dao.models.Customer;
import data.dao.models.Driver;
import javafx.util.Pair;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "choco")
@ApplicationScoped
public class ChocoSolver {

    public ChocoSolver() {
    }

    private List<Driver> selectSpecialDrivers(List<Driver> drivers) {
        List<Driver> specialDrivers = new ArrayList<>();
        for (Driver d : drivers) {
            String carModel = d.getCarModel();
            if (carModel.equals("bmw") || carModel.equals("porsche"))
                specialDrivers.add(d);
        }
        return specialDrivers;
    }

    private List<Customer> getVipCustomers(List<Customer> customers) {
        List<Customer> vipCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            double nr = Math.random();
            if (nr < 0.3)
                vipCustomers.add(customer);
        }
        return vipCustomers;
    }

    public List<Pair<Customer, Driver>> solve() {
        CustomersDAOJpa customersDAOJpa = new CustomersDAOJpa();
        DriversDAOJpa driversDAOJpa = new DriversDAOJpa();

        List<Customer> customers = customersDAOJpa.readAll();
        List<Driver> drivers = driversDAOJpa.readAll();
        List<Driver> specialDrivers = selectSpecialDrivers(drivers);
        List<Customer> vipCustomers = getVipCustomers(customers);

        Model model = new Model();
        IntVar[] allCustomersId = new IntVar[customers.size()];
        IntVar[] allDriversId = new IntVar[drivers.size()];
        IntVar[] specDriversId = new IntVar[specialDrivers.size()];
        IntVar[] specCustomersId = new IntVar[vipCustomers.size()];
        int customersMin = 99, customersMax = 0;
        int driversMin = 99, driversMax = 0;
        for (int i = 0; i < customers.size(); i++)
        {
            int id = customers.get(i).getCustomerId();
            if(id < customersMin)
                customersMin = id;
            if(id > customersMax)
                customersMax = id;
            allCustomersId[i] = model.intVar(id);
        }
        for (int i = 0; i < drivers.size(); i++)
        {
            int id = drivers.get(i).getDriverId();
            if(id < driversMin)
                driversMin = id;
            if(id > driversMax)
                driversMax = id;
            allDriversId[i] = model.intVar(id);
        }
        for (int i = 0; i < specialDrivers.size(); i++)
            specDriversId[i] = model.intVar(specialDrivers.get(i).getDriverId());
        for (int i = 0; i < vipCustomers.size(); i++)
            specCustomersId[i] = model.intVar(vipCustomers.get(i).getCustomerId());

        ArrayList<int[]> assoc = new ArrayList<>();
        for (IntVar integers : allCustomersId) {
            if (isSpecialCustomer(integers, specCustomersId)) {
                for (IntVar intVar : specDriversId) assoc.add(new int[]{integers.getValue(), intVar.getValue()});
            } else {
                for (IntVar intVar : allDriversId) assoc.add(new int[]{integers.getValue(), intVar.getValue()});
            }
        }

        IntVar customerId = model.intVar("customer", customersMin, customersMax);
        IntVar driverId = model.intVar("driver", driversMin, driversMax);
        Constraint c = new Constraint("MDVSPConstraint", new MySimplePropagator(customerId, driverId, assoc));
        model.post(c);
        IntVar n = model.intVar(8);
        model.post(model.atLeastNValues(allCustomersId, n, true));

        List<Pair<Customer, Driver>> result = new ArrayList<>();
        if (model.getSolver().solve()) {
            for (IntVar integers : allCustomersId)
                result.add(new Pair(customers.get(integers.getValue() - 1), drivers.get(integers.getValue() - 1)));
            return result;
        } else
            return null;
    }

    private boolean isSpecialCustomer(IntVar id, IntVar[] specCustomersId) {
        for (IntVar integers : specCustomersId)
            if (integers == id) return true;
        return false;
    }
}
