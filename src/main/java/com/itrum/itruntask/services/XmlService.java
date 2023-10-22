package com.itrum.itruntask.services;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.models.XmlFileModel;
import com.itrum.itruntask.repositories.IXmlFileRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.print.Book;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XmlService {

    private final IXmlFileRepository xmlFileRepository;

    public XmlService(IXmlFileRepository xmlFileRepository) {
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
        XmlFileModel xmlFileModel = new XmlFileModel();
        xmlFileModel.setId(person.getPersonId());
        xmlFileModel.setData(xmlString);

        xmlFileRepository.save(xmlFileModel);

    }

    public List<String> getAllPeople() {
        return xmlFileRepository.findAll().stream().map(XmlFileModel::getData).collect(Collectors.toList());
    }

    public String getPersonByEmail(String email) throws JAXBException, ParserConfigurationException, IOException, SAXException {
        // Pobierz XML z bazy danych
        String xmlString2 = xmlFileRepository.findAll().get(0).getData();
        List<String> xmlData = xmlFileRepository.findAll().stream().map(XmlFileModel::getData).toList();
        var getPersonByEmail = xmlData.stream().filter(x-> getPersonFromXML(x).getEmail().equals(email)).toList();
        System.out.println("--ENG-->" + getPersonByEmail);


        return getPersonByEmail.toString();

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
