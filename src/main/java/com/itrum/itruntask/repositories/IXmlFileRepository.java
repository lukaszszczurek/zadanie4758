package com.itrum.itruntask.repositories;

import com.itrum.itruntask.models.XmlFileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IXmlFileRepository extends JpaRepository<XmlFileModel,String> {
}
