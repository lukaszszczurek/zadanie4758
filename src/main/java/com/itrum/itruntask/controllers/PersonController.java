package com.itrum.itruntask.controllers;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.services.XmlService;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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
    public String getPersonByEmail(@PathVariable String email) throws JAXBException, ParserConfigurationException, IOException, SAXException {
        return xmlService.getPersonByMobile(email);
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
