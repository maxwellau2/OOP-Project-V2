package Pharmacist.Repository;

import Pharmacist.Model.Pharmacist;
import Abstract.Repository;

import java.util.ArrayList;

import static Util.RepositoryGetter.getPharmacistRepository;


public class PharmacistRepository extends Repository<Pharmacist> {
    private static PharmacistRepository instance = null;
    private PharmacistRepository(String csvPath) {
        super(csvPath);
        this.entities = new ArrayList<>();
    }

    public static PharmacistRepository getInstance(String csvPath) {
        if (instance == null) {
            instance = new PharmacistRepository(csvPath);
            instance.load();
        }
        return instance;
    }
    @Override
    protected Pharmacist fromCSV(String csvLine){
        String[] data = csvLine.split(",");
        return new Pharmacist(data[0], data[1], Integer.valueOf(data[2]), data[3]);
    }

    @Override
    protected String getHeader(){
        return "id,name,qualification";
    }

    public static Pharmacist createPharmacist(String name, Integer age, String gender){
        Pharmacist pharmacist = new Pharmacist(getPharmacistRepository().generateId(), name, age, gender);
        return getPharmacistRepository().create(pharmacist);
    }
}



