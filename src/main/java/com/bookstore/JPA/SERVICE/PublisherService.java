package com.bookstore.JPA.SERVICE;

import com.bookstore.JPA.DTOs.Publisher.PublisherRecord;
import com.bookstore.JPA.MODELs.Book;
import com.bookstore.JPA.MODELs.Publisher;
import com.bookstore.JPA.REPOSITORIES.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PublisherService {

    PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository){
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public Publisher savePublisher(PublisherRecord publisherRecord){
        var publisher = new Publisher();
        BeanUtils.copyProperties(publisherRecord,publisher);
        return publisherRepository.save(publisher);
    }

    @Transactional
    public Object listBooksByPublisher(UUID id){
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return "Publisher not found.";
        }
        Set<Book> books = publisherO.get().getBooks();
        if(books.isEmpty()){
            return "Publisher don't have books yet.";
        }
        return books;
    }

    @Transactional
    public Object listAllPublisher(){
        List<Publisher> publishers = publisherRepository.findAll();
        if(publishers.isEmpty()){
            return "There aren't publishers in system yet.";
        }
        return publishers;
    }

    @Transactional
    public Object listOnePublisher(UUID id){
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return "Publisher not found.";
        }
        return publisherO;
    }

    @Transactional
    public String deletePublisher(UUID id){
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return "Publisher not found";
        }
        publisherRepository.delete(publisherO.get());
        return "Delete publisher was doing with success.";
    }

    @Transactional
    public String deletePublishers(List<UUID> ids){
        UUID idPublisher = null;
        try{
            for(UUID id: ids){
                Optional<Publisher> publisherO = publisherRepository.findById(id);
                idPublisher = id;
                if(publisherO.isEmpty()){
                    throw new Exception();
                }
                publisherRepository.delete(publisherO.get());
            }
        }catch(Exception e) {
            return "Publisher with ID = " + idPublisher + ". Not found.";
        }
        return "The process of delete publishers was success.";
    }

    @Transactional
    public Object updatePublisher(UUID id, PublisherRecord publisherRecord){
        Optional<Publisher> publisherO = publisherRepository.findById(id);
        if(publisherO.isEmpty()){
            return "Publisher not found.";
        }
        var publisher = new Publisher();
        BeanUtils.copyProperties(publisherRecord,publisher);
        return publisherRepository.save(publisher);
    }

}
