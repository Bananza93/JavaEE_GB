package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.lesson7.model.HTMLTemplate;

import java.util.List;

public interface HTMLTemplatesRepository extends JpaRepository<HTMLTemplate, Long> {

    List<HTMLTemplate> getHTMLTemplates();

    HTMLTemplate getHTMLTemplatesByDef(String templateDef);
}
