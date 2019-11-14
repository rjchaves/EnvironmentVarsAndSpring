package com.example.demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

@Component
public class EnvProperties {

    @EnvProperty("DATABASE-URL")
    private String databaseUrl;


    @PostConstruct
    public void init() throws IllegalAccessException {
        Dotenv dotenv = Dotenv.load();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            if(f.isAnnotationPresent(EnvProperty.class)) {
                EnvProperty annotation = f.getAnnotation(EnvProperty.class);
                String annotationValue = annotation.value();
                String valueOfTheField = dotenv.get(annotationValue);
                f.set(this, valueOfTheField);
            }
        }
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }
}
