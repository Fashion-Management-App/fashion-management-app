package com.fashion;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(FashionController.class)
public class FashionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FashionService fashionService;

    @Test
    public void indexPageReturnsAllRecords() throws Exception {
        Fashion fashion = new Fashion(
                "Катерина Лисенко", "Maison Aurore", "2024-09-14",
                "Сукня Luna", "груди:90; талія:70", "шовк; мереживо",
                "Gold", 12, "м. Київ, вул. Стильна, 3", 380449910901L);
        Mockito.when(fashionService.viewAllFittings()).thenReturn(List.of(fashion));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("fashions"));
    }

    @Test
    public void addFormReturnsAddView() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add"))
                .andExpect(model().attributeExists("fashion"));
    }

    @Test
    public void addSubmitSavesAndRedirects() throws Exception {
        mockMvc.perform(post("/add")
                .param("customer", "Артем Коваль")
                .param("couturier", "Atelier Nova")
                .param("fittingDate", "2024-09-15")
                .param("itemModel", "Костюм Noir")
                .param("sizes", "груди:98; талія:84")
                .param("fabrics", "шерсть")
                .param("customerLevel", "Silver")
                .param("couturierExperience", "7")
                .param("atelierAddress", "м. Київ, просп. Моди, 8")
                .param("atelierPhone", "380449910902"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(fashionService).saveFitting(Mockito.any(Fashion.class));
    }

    @Test
    public void deleteRemovesRecordAndRedirects() throws Exception {
        mockMvc.perform(post("/delete/some-id"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(fashionService).deleteFittingById("some-id");
    }

    @Test
    public void editFormReturnsEditView() throws Exception {
        Fashion fashion = new Fashion(
                "Катерина Лисенко", "Maison Aurore", "2024-09-14",
                "Сукня Luna", "груди:90; талія:70", "шовк; мереживо",
                "Gold", 12, "м. Київ, вул. Стильна, 3", 380449910901L);
        fashion.setId("test-id");

        Mockito.when(fashionService.getFittingById("test-id"))
                .thenReturn(fashion);

        mockMvc.perform(get("/edit/test-id"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("fashion"));
    }

    @Test
    public void editSubmitUpdatesAndRedirects() throws Exception {
        mockMvc.perform(post("/edit/test-id")
                .param("customer", "Updated Customer")
                .param("couturier", "Updated Atelier")
                .param("fittingDate", "2024-09-16")
                .param("itemModel", "Updated Dress")
                .param("sizes", "L")
                .param("fabrics", "Cotton")
                .param("customerLevel", "VIP")
                .param("couturierExperience", "15")
                .param("atelierAddress", "Kyiv")
                .param("atelierPhone", "380991234567"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Mockito.verify(fashionService)
                .saveFitting(Mockito.any(Fashion.class));
    }
}
