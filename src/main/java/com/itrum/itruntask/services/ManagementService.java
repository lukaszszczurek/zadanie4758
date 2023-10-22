package com.itrum.itruntask.services;

import com.itrum.itruntask.models.PersonModel;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@Service
public class ManagementService {

    public PersonModel getPersonFromXML(String xmlData)
    {
        try {

            xmlData = xmlData.trim().replaceFirst("^([\\W]+)<", "<");
            JAXBContext jaxbContext = JAXBContext.newInstance(PersonModel.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlData);

            return (PersonModel) unmarshaller.unmarshal(reader);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Person not found");
        }
    }
}
