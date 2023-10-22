package com.itrum.itruntask.services;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.models.ExternalPersonXmlModel;
import com.itrum.itruntask.repositories.ExternalPersonXmlRepository;
import com.itrum.itruntask.repositories.InternalPersonXmlRepository;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalPersonXmlService {

    private final ManagementService managementService;
    private final ExternalPersonXmlRepository externalPersonXmlRepository;

    public ExternalPersonXmlService(ExternalPersonXmlRepository externalPersonXmlRepository, InternalPersonXmlRepository internalPersonXmlRepository, ManagementService managementService) {
        this.externalPersonXmlRepository = externalPersonXmlRepository;
        this.managementService = managementService;
    }

    public String getPersonById(String id) {

        var data = externalPersonXmlRepository.findById(id).get().getData();
        if (data == null) {
            throw new RuntimeException("Person not found");
        }
        return data;
    }


    public void addPerson(PersonModel person) throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(PersonModel.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        marshaller.marshal(person, sw);
        String xmlString = sw.toString();
        ExternalPersonXmlModel externalPersonXmlModel = new ExternalPersonXmlModel();
        externalPersonXmlModel.setId(person.getPersonId());
        externalPersonXmlModel.setData(xmlString);

        externalPersonXmlRepository.save(externalPersonXmlModel);
    }

    public List<String> getAllPeople() {
        return externalPersonXmlRepository.findAll().stream().map(ExternalPersonXmlModel::getData).collect(Collectors.toList());
    }

    public String getPersonByType(String tableName)
    {
        return tableName;
    }

    public String getPersonByMobile(String mobile) throws JAXBException, ParserConfigurationException, IOException, SAXException {
        List<String> xmlData = externalPersonXmlRepository.findAll().stream().map(ExternalPersonXmlModel::getData).toList();
        var getPersonByMobile = xmlData.stream().filter(x-> managementService.getPersonFromXML(x).getMobile().equals(mobile)).toList();
        System.out.println("--ENG-->" + getPersonByMobile);

        return getPersonByMobile.toString();
    }

    public String getPersonByFirstName(String firstName) throws JAXBException, ParserConfigurationException, IOException, SAXException {
        List<String> xmlData = externalPersonXmlRepository.findAll().stream().map(ExternalPersonXmlModel::getData).toList();
        var getPersonByFirstName = xmlData.stream()
                .filter(x -> managementService.getPersonFromXML(x).getFirstName().equals(firstName))
                .toList();
        return getPersonByFirstName.toString();
    }

    public String getPersonByEmail(String email) {

        List<String> xmlData = externalPersonXmlRepository.findAll().stream().map(ExternalPersonXmlModel::getData).toList();
        var getPersonByMobile = xmlData.stream()
                .filter(x-> managementService.getPersonFromXML(x).equals(email))
                .toList();
        return getPersonByMobile.toString();
    }
}
