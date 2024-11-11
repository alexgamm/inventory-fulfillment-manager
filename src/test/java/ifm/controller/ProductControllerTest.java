package ifm.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    public void getSellableProducts_getValidProductResponse() {
        mockMvc.perform(get("/api/inventory/products")
                        .param("status", "Sellable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(13)))
                .andExpect(jsonPath("$.products[0].productId").value("p1"))
                .andExpect(jsonPath("$.products[0].status").value("Sellable"))
                .andExpect(jsonPath("$.products[0].fulfilmentCenter").value("fc5"))
                .andExpect(jsonPath("$.products[0].qty").value(4))
                .andExpect(jsonPath("$.products[0].value").value(400));
    }

    @SneakyThrows
    @Test
    public void getUnfulfillableProducts_getValidProductResponse() {
        mockMvc.perform(get("/api/inventory/products")
                        .param("status", "Unfulfillable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(8)))
                .andExpect(jsonPath("$.products[0].productId").value("p2"))
                .andExpect(jsonPath("$.products[0].status").value("Unfulfillable"))
                .andExpect(jsonPath("$.products[0].fulfilmentCenter").value("fc3"))
                .andExpect(jsonPath("$.products[0].qty").value(5))
                .andExpect(jsonPath("$.products[0].value").value(550));
    }

    @SneakyThrows
    @Test
    public void getSellableProductsWithInvalidParam_getBadRequestResponse() {
        String status = "Sellab";
        mockMvc.perform(get("/api/inventory/products")
                        .param("status", status))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.message")
                                .value(String.format("Invalid value '%s' for parameter 'status'.", status))
                );
    }

    @SneakyThrows
    @Test
    public void getSellableProductsValueSum_getTotalValueResponse() {
        mockMvc.perform(get("/api/inventory/products/sellable/total-value")
                        .param("status", "Sellable"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(24870));
    }

    @SneakyThrows
    @Test
    public void getProductsValueForFulfilmentCenter_getTotalValueResponse() {
        mockMvc.perform(get("/api/inventory/products/fcenter/total-value")
                        .param("fulfilmentCenter", "fc5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(12130));
    }

    @SneakyThrows
    @Test
    public void getProductsValueForFulfilmentCenter_getZeroValue() {
        mockMvc.perform(get("/api/inventory/products/fcenter/total-value")
                        .param("fulfilmentCenter", "fc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum").value(0));
    }

}
