package com.fashion.e2e;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FashionE2ETest {

        private static Playwright playwright;
        private static Browser browser;
        private static Page page;

        private static final String BASE_URL = System.getProperty(
                        "E2E_BASE_URL",
                        System.getenv("E2E_BASE_URL"));

        @BeforeAll
        static void setup() {

                Assumptions.assumeTrue(
                                BASE_URL != null && !BASE_URL.isBlank(),
                                "E2E_BASE_URL is not set. Skipping E2E tests.");

                playwright = Playwright.create();

                browser = playwright.chromium().launch(
                                new BrowserType.LaunchOptions()
                                                .setHeadless(true));

                page = browser.newPage();
        }

        @AfterAll
        static void tearDown() {

                if (page != null) {
                        page.close();
                }

                if (browser != null) {
                        browser.close();
                }

                if (playwright != null) {
                        playwright.close();
                }
        }

        @Test
        void shouldOpenMainFashionPage() {

                page.navigate(BASE_URL);

                page.waitForLoadState();
                page.waitForTimeout(3000);

                assertThat(page).hasTitle("Fashion Management");

                assertThat(page.locator("h1"))
                                .containsText("Fashion Management");

                assertThat(page.locator(".btn-add"))
                                .containsText("Add Record");
        }

        @Test
        void shouldOpenAddFashionPage() {

                page.navigate(BASE_URL);

                page.waitForLoadState();
                page.waitForTimeout(3000);

                page.click(".btn-add");

                page.waitForLoadState();
                page.waitForTimeout(2000);

                assertThat(page.locator("h1"))
                                .containsText("Add New Record");

                assertThat(page.locator("button[type='submit']"))
                                .containsText("Save");
        }

        @Test
        void shouldCreateAndDeleteFashionEntry() {

                String customerName = "E2E Test Customer";

                page.navigate(BASE_URL);

                page.waitForLoadState();
                page.waitForTimeout(3000);

                page.click(".btn-add");

                page.waitForLoadState();
                page.waitForTimeout(2000);

                page.locator("input[name='customer']")
                                .fill(customerName);

                page.locator("input[name='couturier']")
                                .fill("Fashion Atelier");

                page.locator("input[name='fittingDate']")
                                .fill("2026-05-07");

                page.locator("input[name='itemModel']")
                                .fill("Evening Dress");

                page.locator("input[name='sizes']")
                                .fill("M");

                page.locator("input[name='fabrics']")
                                .fill("Silk");

                page.locator("input[name='customerLevel']")
                                .fill("Gold");

                page.locator("input[name='couturierExperience']")
                                .fill("10");

                page.locator("input[name='atelierAddress']")
                                .fill("Kyiv");

                page.locator("input[name='atelierPhone']")
                                .fill("380991112233");

                page.click("button[type='submit']");

                page.waitForLoadState();
                page.waitForTimeout(3000);

                assertThat(page.locator("body"))
                                .containsText(customerName);

                page.locator("form:has-text('Delete')")
                                .last()
                                .locator("button")
                                .click();

                page.waitForLoadState();
                page.waitForTimeout(3000);

                page.reload();

                page.waitForLoadState();
                page.waitForTimeout(3000);

                assertThat(page.locator("body"))
                                .not()
                                .containsText(customerName);
        }

        @Test
        void shouldEditFashionEntry() {

                String oldName = "Old Customer";
                String newName = "Updated Customer";

                page.navigate(BASE_URL);

                page.waitForLoadState();
                page.waitForTimeout(3000);

                page.click(".btn-add");

                page.waitForLoadState();
                page.waitForTimeout(2000);

                page.locator("input[name='customer']").fill(oldName);
                page.locator("input[name='couturier']").fill("Fashion Atelier");
                page.locator("input[name='fittingDate']").fill("2026-05-07");
                page.locator("input[name='itemModel']").fill("Evening Dress");
                page.locator("input[name='sizes']").fill("M");
                page.locator("input[name='fabrics']").fill("Silk");
                page.locator("input[name='customerLevel']").fill("Gold");
                page.locator("input[name='couturierExperience']").fill("10");
                page.locator("input[name='atelierAddress']").fill("Kyiv");
                page.locator("input[name='atelierPhone']").fill("380991112233");

                page.click("button[type='submit']");

                page.waitForLoadState();
                page.waitForTimeout(3000);

                assertThat(page.locator("body"))
                                .containsText(oldName);

                page.locator("tr:has-text('Old Customer')")
                                .locator("form:has-text('Edit') button")
                                .click();

                page.waitForLoadState();
                page.waitForTimeout(2000);

                page.locator("input[name='customer']").fill(newName);

                page.click("button[type='submit']");

                page.waitForLoadState();
                page.waitForTimeout(3000);

                assertThat(page.locator("body"))
                                .containsText(newName);

                assertThat(page.locator("body"))
                                .not()
                                .containsText(oldName);
        }
}