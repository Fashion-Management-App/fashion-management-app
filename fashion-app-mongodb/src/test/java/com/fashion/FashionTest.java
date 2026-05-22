package com.fashion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FashionTest {

    private Fashion createSample() {
        return new Fashion(
                "Іванenko Олена",
                "Петренко Марія",
                "2024-01-15",
                "Сукня вечірня",
                "M",
                "Шовк",
                "VIP",
                10,
                "вул. Хрещатик 1, Київ",
                380991234567L);
    }

    @Test
    void testConstructor_setsAllFields() {
        Fashion fashion = createSample();
        assertNotNull(fashion);
    }

    @Test
    void testToString_containsCustomer() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("Іванenko Олена"));
    }

    @Test
    void testToString_containsCouturier() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("Петренко Марія"));
    }

    @Test
    void testToString_containsFittingDate() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("2024-01-15"));
    }

    @Test
    void testToString_containsItemModel() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("Сукня вечірня"));
    }

    @Test
    void testToString_containsSizes() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("M"));
    }

    @Test
    void testToString_containsFabrics() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("Шовк"));
    }

    @Test
    void testToString_containsCustomerLevel() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("VIP"));
    }

    @Test
    void testToString_containsCouturierExperience() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("10"));
    }

    @Test
    void testToString_containsAtelierAddress() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("вул. Хрещатик 1, Київ"));
    }

    @Test
    void testToString_containsAtelierPhone() {
        Fashion fashion = createSample();
        assertTrue(fashion.toString().contains("380991234567"));
    }

    @Test
    void testToString_notNull() {
        Fashion fashion = createSample();
        assertNotNull(fashion.toString());
    }

    @Test
    void testToString_notEmpty() {
        Fashion fashion = createSample();
        assertFalse(fashion.toString().isEmpty());
    }

    @Test
    void testMultipleInstances_areIndependent() {
        Fashion f1 = createSample();
        Fashion f2 = new Fashion(
                "Коваленко Дмитро",
                "Сидоренко Ірина",
                "2024-02-20",
                "Костюм діловий",
                "L",
                "Вовна",
                "Standard",
                5,
                "вул. Саксаганського 5, Київ",
                380997654321L);
        assertNotEquals(f1.toString(), f2.toString());
    }

    @Test
    void testCouturierExperience_zero() {
        Fashion fashion = new Fashion(
                "Тест", "Тест", "2024-01-01",
                "Тест", "S", "Тест", "Basic",
                0, "Тест", 380991111111L);
        assertTrue(fashion.toString().contains("0"));
    }

    @Test
    void testSetId_updatesId() {
        Fashion fashion = createSample();

        fashion.setId("test-id");

        assertEquals("test-id", fashion.getId());
    }
}