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

        System.out.println("--xmlString2-->" + xmlString2);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Usuń niepotrzebny fragment XML z deklaracją XML
            xmlString2 = xmlString2.trim().replaceFirst("^([\\W]+)<", "<");
            Document doc = builder.parse(new InputSource(new StringReader(xmlString2)));

            System.out.println("--doc-->" + doc.toString());

            JAXBContext jaxbContext = JAXBContext.newInstance(PersonModel.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            // Utwórz reader z dokumentu XML
            StringReader reader = new StringReader(xmlString2);
            PersonModel person = (PersonModel) unmarshaller.unmarshal(reader);

            System.out.println("--person-->" + person);

            // Reszta kodu
            List<String> list = xmlFileRepository.findAll().stream().map(XmlFileModel::getData).collect(Collectors.toList());

            return list.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // Obsłuż wyjątki, które mogą wystąpić podczas przetwarzania XML
            return "Błąd analizy XML: " + e.getMessage();
        }
    }


}
