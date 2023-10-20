package com.itrum.itruntask.controllers;

import com.itrum.itruntask.repositories.IXmlFileRepository;
import com.itrum.itruntask.services.PersonService;
import com.itrum.itruntask.services.XmlService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person/api")
public class PersonController {

    private final XmlService xmlSaervice;
    public PersonController( IXmlFileRepository xmlFileRepository, XmlService xmlSaervice, PersonService personService1) {
        this.xmlSaervice = xmlSaervice;

    }

    @GetMapping("/x")
    public String x()
    {
        return "X";
    }


    @GetMapping
    public String getPerson() {
        return xmlSaervice.result();
    }
}
