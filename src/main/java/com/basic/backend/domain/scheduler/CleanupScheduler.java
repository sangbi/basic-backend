package com.basic.backend.domain.scheduler;

import com.basic.backend.domain.service.CleanupService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupScheduler {

    private final CleanupService cleanupService;

    public CleanupScheduler(CleanupService cleanupService) {
        this.cleanupService = cleanupService;
    }

    /**
     * 매일 새벽 3시 삭제된 게시글 물리 정리
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void cleanupOldDeletedPosts() {
        cleanupService.cleanupOldeDeletedPosts();
    }
}
