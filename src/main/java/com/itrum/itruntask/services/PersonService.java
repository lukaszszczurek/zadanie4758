package com.itrum.itruntask.services;

import com.itrum.itruntask.repositories.ExternalPersonXmlRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final ExternalPersonXmlRepository xmlFileRepository;

    public PersonService(ExternalPersonXmlRepository xmlFileRepository) {
        this.xmlFileRepository = xmlFileRepository;
    }


    public String getAllPeople() {
        return xmlFileRepository.findAll().toString();
    }
}
