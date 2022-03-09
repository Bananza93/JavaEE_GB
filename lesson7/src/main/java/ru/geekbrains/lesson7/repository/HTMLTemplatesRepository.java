package ru.geekbrains.lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.lesson7.model.HTMLTemplate;

public interface HTMLTemplatesRepository extends JpaRepository<HTMLTemplate, Long> {
    
    HTMLTemplate getHTMLTemplatesByDef(String templateDef);
}
