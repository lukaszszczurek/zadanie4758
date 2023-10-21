package com.itrum.itruntask.services;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.models.XmlFileModel;
import com.itrum.itruntask.repositories.IXmlFileRepository;
import org.springframework.stereotype.Service;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.print.Book;
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

    public String getPersonByEmail(String email) throws JAXBException {

//        List<XmlFileModel> xmlFileModels = xmlFileRepository.findAll();
//        // set all xmlFilemodels.data to personModel
//        // filter personModel by email
//        // return personModel
//
//      //  String x = "<PersonModel><personId>123</personId><firstName>John</firstName><lastName>Doe</lastName><mobile>123-456-7890</mobile><email>john.doe@example.com</email><pesel>12345678901</pesel></PersonModel>";
//        // x to personModel
//        // personModel.getEmail().equals(email)
//
//        JAXBContext ctx = JAXBContext.newInstance(PersonModel.class);
//        Unmarshaller marshaller =  ctx.createUnmarshaller();
//        StringReader reader = new StringReader(xmlFileModels.get(0).getData());
//        PersonModel personModel = (PersonModel) marshaller.unmarshal(reader);
//        if (personModel.getEmail().equals(email)) {
//            return personModel.toString();
//        }
//        return "not found";
//    }
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<person>\n" +
                "    <personId>5422321</personId>\n" +
                "    <firstName>Jane</firstName>\n" +
                "    <lastName>Smith</lastName>\n" +
                "    <mobile>987-654-3210</mobile>\n" +
                "    <email>janesmith@example.com</email>\n" +
                "    <pesel>98765432109</pesel>\n" +
                "</person>";


        System.out.println(xmlString);

         var xmlString2 = xmlFileRepository.findAll().get(0).getData();

        JAXBContext jaxbContext = JAXBContext.newInstance(PersonModel.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(xmlString2);
        PersonModel person = (PersonModel) unmarshaller.unmarshal(reader);

        System.out.println(person);

        return "h1";

    }



    }
