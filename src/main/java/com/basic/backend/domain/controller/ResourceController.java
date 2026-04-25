package com.basic.backend.domain.controller;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.dto.request.CreateResourceRequest;
import com.basic.backend.domain.dto.request.UpdateResourceRequest;
import com.basic.backend.domain.dto.response.ResourceResponse;
import com.basic.backend.domain.service.ActivityLogService;
import com.basic.backend.domain.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/resources")
public class ResourceController {

    private final ResourceService resourceService;

    private final ActivityLogService activityLogService;

    public ResourceController(ResourceService resourceService,
                              ActivityLogService activityLogService) {
        this.resourceService = resourceService;
        this.activityLogService = activityLogService;
    }

    @GetMapping
    public ApiResponse<List<ResourceResponse>> findAll() {
        return ApiResponse.result(resourceService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ResourceResponse> findById(@PathVariable Long id) {
        return ApiResponse.result(resourceService.findById(id));
    }

    @PostMapping
    public ApiResponse<Long> create(@RequestBody CreateResourceRequest request,
                                      HttpServletRequest httpRequest) {
        Long result = resourceService.create(request);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "RESOURCE_CREATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "title=" + request.getTitle()+
                        ", pinnedYn=" +request.getPinnedYn()+
                        ", status=" +request.getStatus()
        );

        return ApiResponse.result(result);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> update(
            @PathVariable Long id,
            @RequestBody UpdateResourceRequest request,
            HttpServletRequest httpRequest
    ) {
        resourceService.update(id, request);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "RESOURCE_UPDATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "title=" + request.getTitle()+
                        ", pinnedYn=" +request.getPinnedYn()+
                        ", status=" +request.getStatus()
        );

        return ApiResponse.result("자료실 수정 성공");
    }
}