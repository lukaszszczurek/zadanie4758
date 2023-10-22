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
@RequestMapping("/person/api/")
public class PersonController {

    private final ExternalPersonXmlService externalPersonXmlService;
    private final InternalPersonXmlService internalPersonXmlService;
    public PersonController(ExternalPersonXmlService externalPersonXmlService, InternalPersonXmlService internalPersonXmlService) {
        this.externalPersonXmlService = externalPersonXmlService;
        this.internalPersonXmlService = internalPersonXmlService;
    }
    @GetMapping("{tableName}/{id}")
    public String getPersonById(@PathVariable String tableName,@PathVariable String id) throws JAXBException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

       if(tableName.equals("internal")){
           return internalPersonXmlService.getPersonById(id);
       }
       else if(tableName.equals("external")){
           return externalPersonXmlService.getPersonById(id);
       }
       else{
           return "Wrong table name";
       }
    }

    @GetMapping("{tableName}/email/{email}")
    public String getPersonByEmail(@PathVariable String tableName,@PathVariable String email) throws JAXBException, ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      if (tableName.equals("internal")){
          return internalPersonXmlService.getPersonByEmail(email);
      }
      else if(tableName.equals("external")){
          return externalPersonXmlService.getPersonByEmail(email);
      }
      else{
          return "Wrong table name";
      }
    }

    @GetMapping("{tableName}/mobile/{mobile}")
    public String getPersonByMobile(@PathVariable String tableName,@PathVariable String mobile) throws JAXBException, ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (tableName.equals("internal")){
            return internalPersonXmlService.getPersonByMobile(mobile);
        }
        else if(tableName.equals("external")){
            return externalPersonXmlService.getPersonByMobile(mobile);
        }
        else{
            return "Wrong table name";
        }
    }


    @GetMapping("{tableName}/name/{name}")
    public String getPersonByName(@PathVariable String tableName,@PathVariable String name) throws JAXBException, ParserConfigurationException, IOException, SAXException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (tableName.equals("internal")){
            return internalPersonXmlService.getPersonByFirstName(name);
        }
        else if(tableName.equals("external")){
            return externalPersonXmlService.getPersonByFirstName(name);
        }
        else{
            return "Wrong table name";
        }
    }

    @GetMapping("{tableName}")
    public List<String> getAllPeople(@PathVariable String tableName){

        if (tableName.equals("internal")){
            return internalPersonXmlService.getAllPeople();
        }
        else if(tableName.equals("external")){
            return externalPersonXmlService.getAllPeople();
        }
        else{
            throw new RuntimeException("Wrong table name");
        }
    }

    @PostMapping("/{tableName}")
    public void addPerson(@PathVariable String tableName,@RequestBody PersonModel person) throws JAXBException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       if (tableName.equals("internal")){
                internalPersonXmlService.addPerson(person);
            }
            else if(tableName.equals("external")){
                externalPersonXmlService.addPerson(person);
            }
            else{
                System.out.println("Wrong table name");
            }
    }
}
