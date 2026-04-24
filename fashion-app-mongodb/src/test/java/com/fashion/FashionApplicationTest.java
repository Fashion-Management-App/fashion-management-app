package com.fashion;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

class FashionApplicationTest {

    FashionRepository fashionRepository;
    FashionService fashionService;

    @BeforeEach
    void setUp() {
        fashionRepository = mock(FashionRepository.class);
        fashionService = new FashionService(fashionRepository);
    }

    @Test
    void testViewAllFittings_emptyList() {
        when(fashionRepository.findAll()).thenReturn(new ArrayList<>());
        fashionService.viewAllFittings();
        verify(fashionRepository, times(1)).findAll();
    }

    @Test
    void testViewAllFittings_withData() {
        List<Fashion> list = new ArrayList<>();
        list.add(new Fashion(
                "Тест", "Тест", "2024-01-01",
                "Тест", "M", "Тест", "VIP",
                5, "Тест", 380991234567L));
        when(fashionRepository.findAll()).thenReturn(list);
        fashionService.viewAllFittings();
        verify(fashionRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAllFittings() {
        doNothing().when(fashionRepository).deleteAll();
        fashionService.deleteAllFittings();
        verify(fashionRepository, times(1)).deleteAll();
    }

    @Test
    void testImportFashionFromCsv_success() {
        when(fashionRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
        fashionService.importFashionFromCsv();
        verify(fashionRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testImportFashionFromCsv_savesNonEmptyList() {
        when(fashionRepository.saveAll(anyList())).thenAnswer(
                invocation -> invocation.getArgument(0));
        fashionService.importFashionFromCsv();
        verify(fashionRepository, times(1)).saveAll(
                argThat(list -> !((List<?>) list).isEmpty()));
    }

    @Test
    void testViewAllFittings_multipleItems() {
        List<Fashion> list = new ArrayList<>();
        list.add(new Fashion("А", "Б", "2024-01-01", "В", "S", "Г", "VIP",
                3, "Д", 380991111111L));
        list.add(new Fashion("Е", "Є", "2024-02-01", "Ж", "L", "З", "Standard",
                7, "И", 380992222222L));
        list.add(new Fashion("І", "Ї", "2024-03-01", "Й", "XL", "К", "Basic",
                1, "Л", 380993333333L));
        when(fashionRepository.findAll()).thenReturn(list);
        fashionService.viewAllFittings();
        verify(fashionRepository, times(1)).findAll();
    }

    @Test
    void testDeleteAllFittings_calledTwice() {
        doNothing().when(fashionRepository).deleteAll();
        fashionService.deleteAllFittings();
        fashionService.deleteAllFittings();
        verify(fashionRepository, times(2)).deleteAll();
    }

    @Test
    void testViewAllFittings_twoItems() {
        List<Fashion> list = new ArrayList<>();
        list.add(new Fashion("А", "Б", "2024-01-01", "В", "S", "Г", "VIP",
                3, "Д", 380991111111L));
        list.add(new Fashion("Е", "Є", "2024-02-01", "Ж", "L", "З", "Standard",
                7, "И", 380992222222L));
        when(fashionRepository.findAll()).thenReturn(list);
        fashionService.viewAllFittings();
        verify(fashionRepository, times(1)).findAll();
    }

    @Test
    void testImportFashionFromCsv_calledThreeTimes() {
        when(fashionRepository.saveAll(anyList())).thenReturn(new ArrayList<>());
        fashionService.importFashionFromCsv();
        fashionService.importFashionFromCsv();
        fashionService.importFashionFromCsv();
        verify(fashionRepository, times(3)).saveAll(anyList());
    }
}
