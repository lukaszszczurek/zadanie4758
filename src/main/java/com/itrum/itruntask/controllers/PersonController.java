package com.itrum.itruntask.controllers;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.models.XmlFileModel;
import com.itrum.itruntask.repositories.IXmlFileRepository;
import com.itrum.itruntask.services.PersonService;
import com.itrum.itruntask.services.XmlService;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping("/person/api")
public class PersonController {

    private final XmlService xmlService;
    public PersonController(IXmlFileRepository xmlFileRepository, XmlService xmlService, PersonService personService1) {
        this.xmlService = xmlService;

    }

    @GetMapping("/x")
    public String x()
    {
        return "X";
    }




    @GetMapping
    public List<String> getPerson() throws JAXBException {
        return xmlService.result();
    }

    @PostMapping
    public void addPerson(@RequestBody PersonModel person) throws JAXBException {
        XmlFileModel xmlFileModel = new XmlFileModel();
        System.out.println(xmlFileModel.getId() + " ---->");
        xmlFileModel.setData(person.toString());
        System.out.println(xmlFileModel.toString());
        System.out.println("XDDDDDDDDDD");
        System.out.println(person.toString());

        xmlService.addPerson(person);
    }
}
