package com.basic.backend.domain.controller;

import com.basic.backend.core.response.ApiResponse;
import com.basic.backend.domain.dto.request.LinkAttachmentRequest;
import com.basic.backend.domain.dto.response.AttachmentResponse;
import com.basic.backend.domain.entity.AttachmentEntity;
import com.basic.backend.domain.service.AttachmentService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/admin/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ApiResponse<AttachmentResponse> upload(@RequestParam("file") MultipartFile file) {
        return ApiResponse.result(attachmentService.upload(file));
    }

    @GetMapping("/target")
    public ApiResponse<List<AttachmentResponse>> findByTarget(
            @RequestParam String targetType,
            @RequestParam Long targetId
    ) {
        return ApiResponse.result(attachmentService.findByTarget(targetType, targetId));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        AttachmentEntity file = attachmentService.findEntityById(id);

        Resource resource = new FileSystemResource(file.getFilePath());

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(file.getOriginalFileNm(), StandardCharsets.UTF_8)
                                .build()
                                .toString()
                )
                .contentType(MediaType.parseMediaType(
                        file.getContentType() != null ? file.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE
                ))
                .body(resource);
    }

    @PostMapping("/link")
    public ApiResponse<String> link(@RequestBody LinkAttachmentRequest request) {
        attachmentService.linkToTarget(
                request.getAttachmentId(),
                request.getTargetType(),
                request.getTargetId(),
                request.getSortOrder() != null ? request.getSortOrder() : 0
        );

        return ApiResponse.result("첨부파일 연결 성공");
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<Resource> view(@PathVariable Long id) {
        AttachmentEntity file = attachmentService.findEntityById(id);

        Resource resource = new FileSystemResource(file.getFilePath());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        file.getContentType() != null ? file.getContentType() : MediaType.APPLICATION_OCTET_STREAM_VALUE
                ))
                .body(resource);
    }

    @DeleteMapping("/unlink")
    public ApiResponse<String> unlink(@RequestParam Long attachmentId,
                                      @RequestParam String targetType,
                                      @RequestParam Long targetId) {
        attachmentService.unlinkFromTarget(
                attachmentId,
                targetType,
                targetId
        );

        return ApiResponse.result("첨부파일 연결 해제 성공");
    }

    @DeleteMapping("/delete")
    public ApiResponse<String> delete(@RequestParam Long attachmentId,
                                      @RequestParam String targetType,
                                      @RequestParam Long targetId) {
        attachmentService.removeAttachmentFromTarget(
                attachmentId,
                targetType,
                targetId
        );

        return ApiResponse.result("첨부파일 삭제 성공");
    }
}