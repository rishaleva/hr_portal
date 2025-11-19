package ru.ivsk.hrportal.controller.manager.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ManagersResponse {
    private List<ManagerResponse> managers;
    private PageMetadata page;

    public ManagersResponse(List<ManagerResponse> managers) {
        this.managers = managers;
    }

    public ManagersResponse(List<ManagerResponse> managers, PageMetadata page) {
        this.managers = managers;
        this.page = page;
    }

    @Getter
    @Setter
    public static class PageMetadata {

        /**
         *  Номер текущей страницы
         */
        private int number;

        /**
         *  Максимальное количество элементов на одной странице
         */
        private int size;

        /**
         *  Общее количество элементов
         */
        private long totalElements;
        /**
         *  Общее количество страниц
         */
        private int totalPages;

        public static PageMetadata from(Page<?> page) {
            PageMetadata metadata = new PageMetadata();
            metadata.setNumber(page.getNumber());
            metadata.setSize(page.getSize());
            metadata.setTotalElements(page.getTotalElements());
            metadata.setTotalPages(page.getTotalPages());
            return metadata;
        }
    }
}