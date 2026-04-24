package com.fashion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class FashionControllerIT {

    @Container
    static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FashionRepository fashionRepository;

    @BeforeEach
    void setUp() {
        fashionRepository.deleteAll();
    }

    @Test
    void indexPageLoadsAndReturnsEmptyTable() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("fashions"));
    }

    @Test
    void addFormLoadsSuccessfully() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attributeExists("fashion"));
    }

    @Test
    void addRecordSavesToDatabase() throws Exception {
        mockMvc.perform(post("/add")
                        .param("customer", "Катерина Лисенко")
                        .param("couturier", "Maison Aurore")
                        .param("fittingDate", "2024-09-14")
                        .param("itemModel", "Сукня Luna")
                        .param("sizes", "груди:90; талія:70; бедра:95")
                        .param("fabrics", "шовк; мереживо")
                        .param("customerLevel", "Gold")
                        .param("couturierExperience", "12")
                        .param("atelierAddress", "м. Київ, вул. Стильна, 3")
                        .param("atelierPhone", "380449910901"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        List<Fashion> all = fashionRepository.findAll();
        assertEquals(1, all.size());
        assertEquals("Катерина Лисенко", all.get(0).getCustomer());
    }

    @Test
    void deleteRecordRemovesFromDatabase() throws Exception {
        Fashion fashion = new Fashion(
                "Артем Коваль", "Atelier Nova", "2024-09-15",
                "Костюм Noir", "груди:98; талія:84", "шерсть",
                "Silver", 7, "м. Київ, просп. Моди, 8", 380449910902L
        );
        fashionRepository.save(fashion);

        String id = fashionRepository.findAll().get(0).getId();

        mockMvc.perform(post("/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assertTrue(fashionRepository.findAll().isEmpty());
    }

    @Test
    void indexPageShowsSavedRecords() throws Exception {
        fashionRepository.save(new Fashion(
                "Олена Марченко", "KUTURE by Melnyk", "2024-09-19",
                "Пальто Terra", "груди:88; талія:72", "шерсть; кашемір",
                "Platinum", 15, "м. Київ, вул. Подіумна, 11", 380449910903L
        ));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("fashions"));

        assertEquals(1, fashionRepository.findAll().size());
    }
}
