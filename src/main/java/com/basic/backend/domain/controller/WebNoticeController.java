package com.basic.backend.domain.controller;

import com.basic.backend.core.paging.PageRequest;
import com.basic.backend.core.paging.PageResponse;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.CreateNoticeRequest;
import com.basic.backend.domain.dto.request.SearchNoticeCondition;
import com.basic.backend.domain.dto.request.UpdateNoticeRequest;
import com.basic.backend.domain.dto.response.NoticeResponse;
import com.basic.backend.domain.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/notices")
public class WebNoticeController {

    private final NoticeService noticeService;

    public WebNoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    
    @GetMapping
    public ApiResponse<PageResponse<NoticeResponse>> search(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String title
    ) {
        PageRequest<SearchNoticeCondition> request = new PageRequest<SearchNoticeCondition>();
        SearchNoticeCondition params = new SearchNoticeCondition();
        params.setTitle(title);
        params.setStatus("ACTIVE");
        request.setCondition(params);
        request.setPage(page);
        request.setSize(size);
        return ApiResponse.result(noticeService.search(request));
    }
    
    @GetMapping("{id}")
    public ApiResponse<NoticeResponse> findById(@PathVariable Long id) {
        return ApiResponse.result(noticeService.findActiveById(id));
    }
}
