package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.CreateCodeGroupRequest;
import com.basic.backend.domain.dto.request.CreateCodeRequest;
import com.basic.backend.domain.dto.request.UpdateCodeGroupRequest;
import com.basic.backend.domain.dto.request.UpdateCodeRequest;
import com.basic.backend.domain.dto.response.CodeGroupResponse;
import com.basic.backend.domain.dto.response.CodeResponse;
import com.basic.backend.domain.service.CodeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/codes")
public class CodeController {

    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/groups")
    public ApiResponse<List<CodeGroupResponse>> findAllGroups() {
        return ApiResponse.result(codeService.findAllGroups());
    }

    @PostMapping("/groups")
    public ApiResponse<String> createGroup(@RequestBody CreateCodeGroupRequest request) {
        codeService.createGroup(request);
        return ApiResponse.result("코드 그룹 등록 성공");
    }

    @PutMapping("/groups/{id}")
    public ApiResponse<String> updateGroup(
            @PathVariable Long id,
            @RequestBody UpdateCodeGroupRequest request
    ) {
        codeService.updateGroup(id, request);
        return ApiResponse.result("코드 그룹 수정 성공");
    }

    @GetMapping
    public ApiResponse<List<CodeResponse>> findAllCodes() {
        return ApiResponse.result(codeService.findAllCodes());
    }

    @GetMapping("/group/{groupCode}")
    public ApiResponse<List<CodeResponse>> findCodesByGroupCode(@PathVariable String groupCode) {
        return ApiResponse.result(codeService.findCodesByGroupCode(groupCode));
    }

    @PostMapping
    public ApiResponse<String> createCode(@RequestBody CreateCodeRequest request) {
        codeService.createCode(request);
        return ApiResponse.result("코드 등록 성공");
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateCode(
            @PathVariable Long id,
            @RequestBody UpdateCodeRequest request
    ) {
        codeService.updateCode(id, request);
        return ApiResponse.result("코드 수정 성공");
    }
}