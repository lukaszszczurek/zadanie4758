package com.itrum.itruntask.controllers;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.models.XmlFileModel;
import com.itrum.itruntask.repositories.IXmlFileRepository;
import com.itrum.itruntask.services.PersonService;
import com.itrum.itruntask.services.XmlService;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping("/person/api")
public class PersonController {

    private final XmlService xmlService;
    public PersonController( XmlService xmlService) {
        this.xmlService = xmlService;
    }
    @GetMapping("/{id}")
    public String getPersonById(@PathVariable String id) throws JAXBException {
        return xmlService.getPersonById(id);
    }

    @GetMapping("XD/{email}")
    public String getPersonByEmail(@PathVariable String email) throws JAXBException {
        return xmlService.getPersonByEmail(email);
    }

    @GetMapping
    public List<String> getAllPeople() {
        return xmlService.getAllPeople();
    }

    @PostMapping
    public void addPerson(@RequestBody PersonModel person) throws JAXBException {
        xmlService.addPerson(person);
    }
}
