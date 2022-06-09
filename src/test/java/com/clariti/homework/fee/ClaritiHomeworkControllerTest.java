package com.clariti.homework.fee;

import com.clariti.homework.fee.model.FeeRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Terry Deng
 */

@SpringBootTest
public class ClaritiHomeworkControllerTest {
    @Mock
    private ClaritiHomeworkService claritiHomeworkService;

    @InjectMocks
    private ClaritiHomeworkController claritiHomeworkController;

    @Test
    public void testGetTotalFee() {
        when(claritiHomeworkService.getTotalFee(nullable(FeeRequest.class)))
                .thenReturn(BigDecimal.valueOf(1500));

        BigDecimal feeWithSurcharge = claritiHomeworkController.getTotalFee(nullable(FeeRequest.class));
        verify(claritiHomeworkService, times(1)).getTotalFee(nullable(FeeRequest.class));
        assertEquals(BigDecimal.valueOf(1500), feeWithSurcharge);
    }

    @Test
    public void testGetTotalFee_Exception() {
        when(claritiHomeworkService.getTotalFee(nullable(FeeRequest.class)))
                .thenThrow(FeeException.class);

        assertThrows(FeeException.class, () -> claritiHomeworkController.getTotalFee(nullable(FeeRequest.class)));
    }
}
