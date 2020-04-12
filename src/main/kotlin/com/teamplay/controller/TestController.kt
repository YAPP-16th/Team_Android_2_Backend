package com.teamplay.controller

import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/tests")
class TestController {

    @ApiOperation(value = "테스트 API")
    @PostMapping("/test/{testId}")
    @ResponseStatus(HttpStatus.OK)
    fun checkDuplicationUserId(@Valid @PathVariable testId: String) : String{

        return "테스트번호 $testId"
    }


}
