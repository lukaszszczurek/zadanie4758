package com.itrum.itruntask.services;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.models.ExternalPersonXmlModel;
import com.itrum.itruntask.repositories.ExternalPersonXmlRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XmlService {

    private final ExternalPersonXmlRepository xmlFileRepository;

    public XmlService(ExternalPersonXmlRepository xmlFileRepository) {
        this.xmlFileRepository = xmlFileRepository;
    }

    public String getPersonById(String id) {

        var data = xmlFileRepository.findById(id).get().getData();
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

        xmlFileRepository.save(externalPersonXmlModel);

    }

    public List<String> getAllPeople() {
        return xmlFileRepository.findAll().stream().map(ExternalPersonXmlModel::getData).collect(Collectors.toList());
    }

    public String getPersonByType(String tableName)
    {
        return tableName;
    }

    public String getPersonByMobile(String mobile) throws JAXBException, ParserConfigurationException, IOException, SAXException {
        List<String> xmlData = xmlFileRepository.findAll().stream().map(ExternalPersonXmlModel::getData).toList();
        var getPersonByMobile = xmlData.stream().filter(x-> getPersonFromXML(x).getMobile().equals(mobile)).toList();
        System.out.println("--ENG-->" + getPersonByMobile);


        return getPersonByMobile.toString();

    }

    public String getPersonByFirstName(String firstName) throws JAXBException, ParserConfigurationException, IOException, SAXException {
        // Pobierz XML z bazy danych
        List<String> xmlData = xmlFileRepository.findAll().stream().map(ExternalPersonXmlModel::getData).toList();
        var getPersonByFirstName = xmlData.stream().filter(x -> getPersonFromXML(x).getFirstName().equals(firstName)).toList();
        System.out.println("--ENG-->" + getPersonByFirstName);

        return getPersonByFirstName.toString();
    }




    public PersonModel getPersonFromXML(String xmlData)
    {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();
            // Usuń niepotrzebny fragment XML z deklaracją XML
            xmlData = xmlData.trim().replaceFirst("^([\\W]+)<", "<");
            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

            System.out.println("--doc-->" + doc.toString());

            JAXBContext jaxbContext = JAXBContext.newInstance(PersonModel.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Utwórz reader z dokumentu XML
            StringReader reader = new StringReader(xmlData);
            PersonModel person = (PersonModel) unmarshaller.unmarshal(reader);

            System.out.println("--person-->" + person);

            System.out.println("--person-->" + person.getEmail());
            System.out.println("--person-->" + person.getFirstName());

            return person;
        } catch (Exception e) {
            e.printStackTrace();
            // Obsłuż wyjątki, które mogą wystąpić podczas przetwarzania XML
           throw new RuntimeException("Person not found");
        }
    }



}
