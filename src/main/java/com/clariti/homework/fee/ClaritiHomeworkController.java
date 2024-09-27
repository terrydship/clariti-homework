package com.clariti.homework.fee;

import com.clariti.homework.fee.model.FeeRequest;
import com.clariti.homework.fee.model.TotalFee;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Terry Deng
 */

@RestController
@RequiredArgsConstructor
public class ClaritiHomeworkController {

    private final ClaritiHomeworkService claritiHomeworkService;

    @ApiOperation("Calculate the total fee given a combination of department, category, sub category and type.")
    @PostMapping("/fees")
    public TotalFee getTotalFee(@RequestBody FeeRequest feeRequest) {
        return claritiHomeworkService.getTotalFee(feeRequest);
    }
}
