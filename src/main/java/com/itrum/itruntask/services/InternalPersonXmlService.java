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

@Service
public class InternalPersonXmlService {

    InternalPersonXmlRepository internalPersonXmlRepository;

    public InternalPersonXmlService(InternalPersonXmlRepository internalPersonXmlRepository) {
        this.internalPersonXmlRepository = internalPersonXmlRepository;
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



}
