package com.clariti.homework.fee;

import com.clariti.homework.fee.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Terry Deng
 */

@SpringBootTest
@AutoConfigureMockMvc
public class ClaritiHomeworkControllerTest {
    @MockBean
    private ClaritiHomeworkService claritiHomeworkService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTotalFee() throws Exception {
        when(claritiHomeworkService.getTotalFee(nullable(FeeRequest.class)))
                .thenReturn(new TotalFee(BigDecimal.valueOf(1500)));

        FeeRequest feeRequest = new FeeRequest(Department.DEVELOPMENT, Category.CODING, SubCategory.CAT_1, Type.TYPE_A);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/fees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(feeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.feeWithSurcharge", is(1500)));

        verify(claritiHomeworkService, times(1)).getTotalFee(nullable(FeeRequest.class));
    }

    @Test
    public void testGetTotalFee_Exception() throws Exception {
        when(claritiHomeworkService.getTotalFee(nullable(FeeRequest.class)))
                .thenThrow(FeeException.class);

        FeeRequest feeRequest = new FeeRequest(Department.DEVELOPMENT, Category.CODING, SubCategory.CAT_1, Type.TYPE_A);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/fees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(feeRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof FeeException));
    }

    private static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
