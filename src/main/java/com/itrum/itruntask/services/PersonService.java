package com.itrum.itruntask.services;

import com.itrum.itruntask.repositories.IXmlFileRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final IXmlFileRepository xmlFileRepository;

    public PersonService(IXmlFileRepository xmlFileRepository) {
        this.xmlFileRepository = xmlFileRepository;
    }


    public String getAllPeople() {
        return xmlFileRepository.findAll().toString();
    }
}
