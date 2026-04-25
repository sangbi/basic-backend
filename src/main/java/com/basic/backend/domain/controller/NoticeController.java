package com.basic.backend.domain.controller;

import com.basic.backend.core.auth.util.SecurityUtil;
import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.core.util.RequestInfoUtil;
import com.basic.backend.domain.dto.request.CreateNoticeRequest;
import com.basic.backend.domain.dto.request.UpdateNoticeRequest;
import com.basic.backend.domain.dto.response.NoticeResponse;
import com.basic.backend.domain.service.ActivityLogService;
import com.basic.backend.domain.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/notices")
public class NoticeController {
    
    private final NoticeService noticeService;
    
    private final ActivityLogService activityLogService;
    
    public NoticeController(NoticeService noticeService,
                            ActivityLogService activityLogService) {
        this.noticeService = noticeService;
        this.activityLogService = activityLogService;
    }
    
    @GetMapping
    public ApiResponse<List<NoticeResponse>> findAll() {
        return ApiResponse.result(noticeService.findAll());
    }
    
    @GetMapping("{id}")
    public ApiResponse<NoticeResponse> findById(@PathVariable Long id) {
        return ApiResponse.result(noticeService.findById(id));
    }
    
    @PostMapping
    public ApiResponse<Long> create(@RequestBody CreateNoticeRequest request,
                                      HttpServletRequest httpRequest) {
        Long result = noticeService.create(request);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "NOTICE_CREATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "title=" + request.getTitle()+
                        ", noticeType=" +request.getNoticeType()+
                        ", pinnedYn=" +request.getPinnedYn()+
                        ", status=" +request.getStatus()
        );
        
        return ApiResponse.result(result);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<String> update(@PathVariable Long id,
                                      @RequestBody UpdateNoticeRequest request,
                                      HttpServletRequest httpRequest) {
        noticeService.update(id,request);

        activityLogService.save(
                SecurityUtil.getCurrentUserId(),
                "NOTICE_UPDATE",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                RequestInfoUtil.getClientIp(httpRequest),
                RequestInfoUtil.getUserAgent(httpRequest),
                "200",
                "title=" + request.getTitle()+
                        ", noticeType=" +request.getNoticeType()+
                        ", pinnedYn=" +request.getPinnedYn()+
                        ", status=" +request.getStatus()
        );
        
        return ApiResponse.result("공지사항 수정 성공");
    }
}
