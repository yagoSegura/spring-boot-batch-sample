package com.starwars_batch.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "people")
@Entity
public class People {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String birthYear;
    private String gender;
    private String height;
    private String mass;
    private String eyeColor;
    private String hairColor;
    private String skinColor;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
