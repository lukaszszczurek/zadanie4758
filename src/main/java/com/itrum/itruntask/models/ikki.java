package com.itrum.itruntask.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
public class ikki {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "data")
    private String data;

}
