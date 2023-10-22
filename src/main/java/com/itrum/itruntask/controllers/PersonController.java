package com.itrum.itruntask.controllers;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.services.ExternalPersonXmlService;
import com.itrum.itruntask.services.InternalPersonXmlService;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/person/api")
public class PersonController {

    private final ExternalPersonXmlService externalPersonXmlService;
    private final InternalPersonXmlService internalPersonXmlService;
    public PersonController(ExternalPersonXmlService externalPersonXmlService, InternalPersonXmlService internalPersonXmlService) {
        this.externalPersonXmlService = externalPersonXmlService;
        this.internalPersonXmlService = internalPersonXmlService;
    }
    @GetMapping("{tableName}/{id}")
    public String getPersonById(@PathVariable String tableName,@PathVariable String id) throws JAXBException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return serviceManager(tableName).getClass()
                .getMethod("getPersonById", String.class)
                .invoke(serviceManager(tableName), id).toString();
    }

    @GetMapping("{tableName}/{email}")
    public String getPersonByEmail(@PathVariable String tableName,@PathVariable String email) throws JAXBException, ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return serviceManager(tableName).getClass()
                .getMethod("getPersonByEmail", String.class)
                .invoke(serviceManager(tableName), email).toString();
    }

    @GetMapping
    public List<String> getAllPeople() {
        return externalPersonXmlService.getAllPeople();
    }

    @PostMapping("/{tableName}")
    public void addPerson(@PathVariable String tableName,@RequestBody PersonModel person) throws JAXBException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        serviceManager(tableName).getClass()
                .getMethod("addPerson", PersonModel.class)
                .invoke(serviceManager(tableName), person);
    }

    // manager

    public <T> T serviceManager(String tableName) throws JAXBException {
        if(tableName.equals("internal")){
            return (T) internalPersonXmlService;
        }
        else if(tableName.equals("external")){
            return (T) externalPersonXmlService;
        }
        else{
            throw new RuntimeException("Table not found");
        }
    }
}
