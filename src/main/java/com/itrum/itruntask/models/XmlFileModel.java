package com.itrum.itruntask.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "external")
public class XmlFileModel {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "data")
    private String data;
}
