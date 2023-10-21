package com.itrum.itruntask.services;

import com.itrum.itruntask.models.PersonModel;
import com.itrum.itruntask.models.XmlFileModel;
import com.itrum.itruntask.repositories.IXmlFileRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XmlService {

    private final IXmlFileRepository xmlFileRepository;


    public XmlService(IXmlFileRepository xmlFileRepository) {
        this.xmlFileRepository = xmlFileRepository;
    }

    public List<String> result() throws JAXBException {
        var data = xmlFileRepository.findAll();


        JAXBContext ctx = JAXBContext.newInstance(PersonModel.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PersonModel product = new PersonModel("1", "Jan", "kowal", "43333", "5@.plp", "PESEL");
        StringWriter sw = new StringWriter();
        marshaller.marshal(product, sw);
        String xmlString = sw.toString();
        System.out.println(xmlString);


        return data.stream().map(XmlFileModel::getData).collect(Collectors.toList());


    }
}
