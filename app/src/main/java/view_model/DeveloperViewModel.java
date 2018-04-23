package view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import entity.Developer;
import entity.Publisher;
import repository.DeveloperRepository;
import repository.PublisherRepository;

/**
 * Created by Dodskamp on 23.04.2018.
 */

public class DeveloperViewModel extends AndroidViewModel {

    private DeveloperRepository developerRepository;
    private List<Developer> allDeveloper;

    public DeveloperViewModel(@NonNull Application application) {
        super(application);
        developerRepository = new DeveloperRepository(application);
        allDeveloper = developerRepository.getAllPublisher();
    }

    public List<Developer> getAllDeveloper() {
        return allDeveloper;
    }

    public void insert(Developer developer) {
        developerRepository.insert(developer);
    }

}
