package com.fashion.e2e;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FashionE2ETest {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    private static final String BASE_URL = System.getProperty(
            "E2E_BASE_URL",
            System.getenv("E2E_BASE_URL"));

    @BeforeAll
    static void setupBrowser() {
        Assumptions.assumeTrue(
                BASE_URL != null && !BASE_URL.isBlank(),
                "E2E_BASE_URL is not set. Skipping E2E tests.");

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true));
    }

    @AfterAll
    static void tearDownBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void setupPage() {
        page = browser.newPage();
    }

    @AfterEach
    void closePage() {
        if (page != null) page.close();
    }

    private void openHomePage() {
        page.navigate(BASE_URL);

        page.waitForFunction(
                "() => document.title === 'Fashion Management'",
                null,
                new Page.WaitForFunctionOptions().setTimeout(60000)
        );

        assertThat(page).hasTitle("Fashion Management");
    }

    @Test
    void shouldOpenMainFashionPage() {
        openHomePage();

        assertThat(page.locator("h1"))
                .containsText("Fashion Management");

        assertThat(page.locator(".btn-add"))
                .containsText("Add Record");
    }

    @Test
    void shouldOpenAddFashionPage() {
        openHomePage();

        page.click(".btn-add");

        page.waitForLoadState();

        assertThat(page.locator("h1"))
                .containsText("Add New Record");

        assertThat(page.locator("button[type='submit']"))
                .containsText("Save");
    }

    @Test
    void shouldCreateAndDeleteFashionEntry() {
        String customerName = "E2E Test Customer";

        openHomePage();

        page.click(".btn-add");
        page.waitForLoadState();

        fillForm(customerName);

        page.click("button[type='submit']");
        page.waitForLoadState();

        assertThat(page.locator("body"))
                .containsText(customerName);

        page.locator("tr:has-text('" + customerName + "')")
                .locator("form:has-text('Delete') button")
                .click();

        page.waitForLoadState();
        page.reload();

        assertThat(page.locator("body"))
                .not()
                .containsText(customerName);
    }

    @Test
    void shouldEditFashionEntry() {
        String oldName = "Old Customer";
        String newName = "Updated Customer";

        openHomePage();

        page.click(".btn-add");
        page.waitForLoadState();

        fillForm(oldName);

        page.click("button[type='submit']");
        page.waitForLoadState();

        assertThat(page.locator("body"))
                .containsText(oldName);

        page.locator("tr:has-text('" + oldName + "')")
                .locator("form:has-text('Edit') button")
                .click();

        page.waitForLoadState();

        page.locator("input[name='customer']")
                .fill(newName);

        page.click("button[type='submit']");
        page.waitForLoadState();

        assertThat(page.locator("body"))
                .containsText(newName);

        assertThat(page.locator("body"))
                .not()
                .containsText(oldName);
    }

    private void fillForm(String customerName) {
        page.locator("input[name='customer']").fill(customerName);
        page.locator("input[name='couturier']").fill("Fashion Atelier");
        page.locator("input[name='fittingDate']").fill("2026-05-07");
        page.locator("input[name='itemModel']").fill("Evening Dress");
        page.locator("input[name='sizes']").fill("M");
        page.locator("input[name='fabrics']").fill("Silk");
        page.locator("input[name='customerLevel']").fill("Gold");
        page.locator("input[name='couturierExperience']").fill("10");
        page.locator("input[name='atelierAddress']").fill("Kyiv");
        page.locator("input[name='atelierPhone']").fill("380991112233");
    }
}