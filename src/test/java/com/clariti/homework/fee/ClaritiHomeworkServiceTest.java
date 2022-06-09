package com.clariti.homework.fee;

import com.clariti.homework.fee.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Terry Deng
 */

@SpringBootTest
public class ClaritiHomeworkServiceTest {
    @Spy
    private ClaritiHomeworkService claritiHomeworkService;

    private FeeRequest feeRequest;

    @BeforeEach
    void init() throws IOException {
        claritiHomeworkService.loadRawFees();
    }

    @Test
    public void testGetTotalFee_1() {
        feeRequest = new FeeRequest(Department.DEVELOPMENT, Category.QUALITY_ASSURANCE, SubCategory.CAT_1, null);
        BigDecimal feeWithSurcharge = claritiHomeworkService.getTotalFee(feeRequest);
        assertEquals(BigDecimal.valueOf(110212), feeWithSurcharge);
    }

    @Test
    public void testGetTotalFee_2() {
        feeRequest = new FeeRequest(Department.OPERATIONS, Category.HUMAN_RESOURCES, null, null);
        BigDecimal feeWithSurcharge = claritiHomeworkService.getTotalFee(feeRequest);
        assertEquals(BigDecimal.valueOf(229041), feeWithSurcharge);
    }

    @Test
    public void testGetTotalFee_3() {
        feeRequest = new FeeRequest(Department.DEVELOPMENT, null, SubCategory.CAT_2, Type.TYPE_A);
        BigDecimal feeWithSurcharge = claritiHomeworkService.getTotalFee(feeRequest);
        assertEquals(BigDecimal.valueOf(63979), feeWithSurcharge);
    }

    @Test
    public void testGetTotalFee_Invalid_Department() {
        feeRequest = new FeeRequest(null, Category.QUALITY_ASSURANCE, SubCategory.CAT_1, null);
        Exception exception = assertThrows(FeeException.class, () -> claritiHomeworkService.getTotalFee(feeRequest));
        assertEquals(ClaritiHomeworkService.INVALID_DEPARTMENT, exception.getMessage());
    }

    @Test
    public void testGetTotalFee_Invalid_Category() {
        feeRequest = new FeeRequest(Department.DEVELOPMENT, Category.TIER_1, SubCategory.CAT_1, Type.TYPE_A);
        Exception exception = assertThrows(FeeException.class, () -> claritiHomeworkService.getTotalFee(feeRequest));
        assertEquals(ClaritiHomeworkService.INVALID_CATEGORY, exception.getMessage());
    }
}
