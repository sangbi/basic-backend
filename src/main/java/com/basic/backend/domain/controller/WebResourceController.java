package com.basic.backend.domain.controller;

import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.CreateResourceRequest;
import com.basic.backend.domain.dto.request.SearchResourceCondition;
import com.basic.backend.domain.dto.request.UpdateResourceRequest;
import com.basic.backend.domain.dto.response.ResourceResponse;
import com.basic.backend.domain.service.ResourceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/resources")
public class WebResourceController {

    private final ResourceService resourceService;

    public WebResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public ApiResponse<PageResponse<ResourceResponse>> search(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String title
    ) {
        PageRequest<SearchResourceCondition> request = new PageRequest<SearchResourceCondition>();
        SearchResourceCondition params = new SearchResourceCondition();
        params.setTitle(title);
        params.setStatus("ACTIVE");
        request.setCondition(params);
        request.setPage(page);
        request.setSize(size);
        return ApiResponse.result(resourceService.search(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<ResourceResponse> findById(@PathVariable Long id) {
        return ApiResponse.result(resourceService.findActiveById(id));
    }
}