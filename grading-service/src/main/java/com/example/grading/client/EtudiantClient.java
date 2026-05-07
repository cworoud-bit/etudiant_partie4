package com.example.grading.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// "etudiant-service" houwa el issem elli t'9ayed bih el microservice lakher fi Eureka
@FeignClient(name = "etudiant-service")
public interface EtudiantClient {

    @GetMapping("/api/etudiants/{id}")
    Object getEtudiantById(@PathVariable("id") Long id);
}