package com.itrum.itruntask.services;

import com.itrum.itruntask.repositories.IXmlFileRepository;
import org.springframework.stereotype.Service;

@Service
public class XmlService {

    private final IXmlFileRepository xmlFileRepository;


    public XmlService(IXmlFileRepository xmlFileRepository) {
        this.xmlFileRepository = xmlFileRepository;
    }

    public  String result()
    {
        var data = xmlFileRepository.findAll();
        var w = data.get(0);
        return w.getData();
    }
}
