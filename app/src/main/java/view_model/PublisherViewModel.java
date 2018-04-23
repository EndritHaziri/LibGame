package view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import entity.Publisher;
import repository.PublisherRepository;

/**
 * Created by Dodskamp on 23.04.2018.
 */

public class PublisherViewModel extends AndroidViewModel {

    private PublisherRepository publisherRepository;
    private List<Publisher> allPublisher;

    public PublisherViewModel(@NonNull Application application) {
        super(application);
        publisherRepository = new PublisherRepository(application);
        allPublisher = publisherRepository.getAllPublisher();
    }

    public List<Publisher> getAllPublisher() {
        return allPublisher;
    }

    public void insert(Publisher publisher) {
        publisherRepository.insert(publisher);
    }

}
