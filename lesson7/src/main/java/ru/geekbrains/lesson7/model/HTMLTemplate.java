package ru.geekbrains.lesson7.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "html_templates")
@Data
@NoArgsConstructor
public class HTMLTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String def;

    @Column
    private String template;
}
