package com.itrum.itruntask.services;

import com.itrum.itruntask.models.ExternalPersonXmlModel;
import com.itrum.itruntask.models.InternalPersonXmlModel;
import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.repositories.InternalPersonXmlRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

@Service
public class InternalPersonXmlService {

    private final InternalPersonXmlRepository internalPersonXmlRepository;
    private final ManagementService managementService;

    public InternalPersonXmlService(InternalPersonXmlRepository internalPersonXmlRepository, ManagementService managementService) {
        this.internalPersonXmlRepository = internalPersonXmlRepository;
        this.managementService = managementService;
    }


    public void addPerson(PersonModel person) throws JAXBException {

        JAXBContext ctx = JAXBContext.newInstance(PersonModel.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        marshaller.marshal(person, sw);
        String xmlString = sw.toString();
        InternalPersonXmlModel internalPersonXmlModel = new InternalPersonXmlModel();
        internalPersonXmlModel.setId(person.getPersonId());
        internalPersonXmlModel.setData(xmlString);

        internalPersonXmlRepository.save(internalPersonXmlModel);

    }


    public String getPersonById(String id) {
        var data = internalPersonXmlRepository.findById(id).get().getData();
        if (data == null) {
            throw new RuntimeException("Person not found");
        }
        return data;
    }

    public String getPersonByEmail(String email) {
        List<String> xmlData = internalPersonXmlRepository.findAll().stream().map(InternalPersonXmlModel::getData).toList();
        var getPersonByEmail = xmlData.stream().filter(x-> managementService.getPersonFromXML(x).getEmail().equals(email)).toList();

        return getPersonByEmail.toString();
    }

    public String getPersonByMobile(String mobile) {
        List<String> xmlData = internalPersonXmlRepository.findAll().stream().map(InternalPersonXmlModel::getData).toList();
        var getPersonByMobile = xmlData.stream()
                .filter(x-> managementService.getPersonFromXML(x).getMobile().equals(mobile))
                .toList();
        return getPersonByMobile.toString();
    }

    public List<String> getAllPeople() {
        return internalPersonXmlRepository.findAll().stream().map(InternalPersonXmlModel::getData).toList();
    }

    public String getPersonByFirstName(String name) {
        List<String> xmlData = internalPersonXmlRepository.findAll().stream().map(InternalPersonXmlModel::getData).toList();
        var getPersonByFirstName = xmlData.stream()
                .filter(x -> managementService.getPersonFromXML(x).getFirstName().equals(name))
                .toList();
        return getPersonByFirstName.toString();
    }
}
