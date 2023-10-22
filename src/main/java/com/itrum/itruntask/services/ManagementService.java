package com.itrum.itruntask.services;

import org.springframework.stereotype.Service;

@Service
public class ManagementService {

    private final InternalPersonXmlService personService;
    private final ExternalPersonXmlService externalPersonXmlService;

    public ManagementService(InternalPersonXmlService personService, ExternalPersonXmlService externalPersonXmlService) {
        this.personService = personService;
        this.externalPersonXmlService = externalPersonXmlService;
    }




}
