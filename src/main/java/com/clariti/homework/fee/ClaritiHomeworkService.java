package com.clariti.homework.fee;

import com.clariti.homework.fee.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Terry Deng
 */

@Service
public class ClaritiHomeworkService {

    public static final String INVALID_DEPARTMENT = "Invalid Department";
    public static final String INVALID_CATEGORY = "Invalid Category";
    public static final String INVALID_SUBCATEGORY = "Invalid Sub Category";
    public static final String INVALID_TYPE = "Invalid Type";

    private final List<Fee> feeList = new ArrayList<>();

    /**
     * Calculate the total fee given a combination of department, category, sub category and type
     * <p>
     * The idea is to group the list of raw fees by, in the order of department, category, sub category and type if provided.
     * Once done, stream the grouped list and use a reducer to calculate the total fee, then apply the surcharge based on department.
     *
     * @param feeRequest a combination of department, category, sub category and type
     * @return Total fee
     */
    public TotalFee getTotalFee(FeeRequest feeRequest) {
        if (feeRequest.getDepartment() == null) {
            throw new FeeException(INVALID_DEPARTMENT);
        }

        Map<Enum<?>, List<Fee>> groupedFeeMap;
        List<Fee> groupedFeeList = new ArrayList<>();

        groupedFeeMap = feeList.stream().collect(Collectors.groupingBy(Fee::getDepartment));
        if (feeRequest.getDepartment() != null) {
            if (!groupedFeeMap.containsKey(feeRequest.getDepartment())) {
                throw new FeeException(INVALID_DEPARTMENT);
            } else {
                groupedFeeList = groupedFeeMap.get(feeRequest.getDepartment());
            }
        }

        groupedFeeMap = groupedFeeList.stream().collect(Collectors.groupingBy(Fee::getCategory));
        if (feeRequest.getCategory() != null) {
            if (!groupedFeeMap.containsKey(feeRequest.getCategory())) {
                throw new FeeException(INVALID_CATEGORY);
            } else {
                groupedFeeList = groupedFeeMap.get(feeRequest.getCategory());
            }
        }

        groupedFeeMap = groupedFeeList.stream().collect(Collectors.groupingBy(Fee::getSubCategory));
        if (feeRequest.getSubCategory() != null) {
            if (!groupedFeeMap.containsKey(feeRequest.getSubCategory())) {
                throw new FeeException(INVALID_SUBCATEGORY);
            } else {
                groupedFeeList = groupedFeeMap.get(feeRequest.getSubCategory());
            }
        }

        groupedFeeMap = groupedFeeList.stream().collect(Collectors.groupingBy(Fee::getType));
        if (feeRequest.getType() != null) {
            if (!groupedFeeMap.containsKey(feeRequest.getType())) {
                throw new FeeException(INVALID_TYPE);
            } else {
                groupedFeeList = groupedFeeMap.get(feeRequest.getType());
            }
        }

        BigDecimal totalFee = groupedFeeList.stream().map(Fee::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TotalFee(applySurcharge(totalFee, feeRequest.getDepartment()));
    }

    private BigDecimal applySurcharge(BigDecimal totalFee, Department department) {
        BigDecimal feeWithSurcharge = totalFee.multiply(BigDecimal.valueOf(department.getSurcharge()));
        return feeWithSurcharge.setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * Load the raw fees on application startup
     * @throws IOException if an I/O error occurs opening the file
     */
    @PostConstruct
    public void loadRawFees() throws IOException {
        Path filePath = Paths.get("src/main/resources/raw_fees.csv");
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1).forEach(line -> {
                String[] columns = line.split(",");
                String id = columns[0];
                String name = columns[1];
                String description = columns[2];
                Department department = Department.fromText(columns[3]);
                Category category = Category.fromText(columns[4]);
                SubCategory subCategory = SubCategory.fromText(columns[5]);
                Type type = Type.fromText(columns[6]);
                Integer quantity = Integer.valueOf(columns[7]);
                BigDecimal unitPrice = new BigDecimal(columns[8]);

                Fee fee = new Fee(id, name, description, department, category, subCategory, type, quantity, unitPrice);
                feeList.add(fee);
            });
        }
    }
}
