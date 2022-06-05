package yjp.wp.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageableList<T> {
    private boolean isFirst;
    private boolean isLast;
    private boolean isMoreLeft;
    private boolean isMoreRight;
    private int listMin;
    private int listMax;
    private int number;
    private int totalPage;
    private List<T> content;

    public PageableList(Page<T> page) {
        isFirst = page.isFirst();
        isLast = page.isLast();
        number = page.getNumber() + 1;
        totalPage = page.getTotalPages();
        calculateList();
        content = page.getContent();
    }

    private void calculateList() {
        if (totalPage <= 1) {
            isFirst = true;
            isLast = true;
            return;
        }
        if (totalPage < 9) {
            listMin = 2;
            listMax = totalPage - 1;
            return;
        }
        if (number < 4) {
            isMoreRight = true;
            listMin = 2;
            listMax = 7;
            return;
        }
        if (totalPage - number < 4) {
            isMoreLeft = true;
            listMin = totalPage - 6;
            listMax = totalPage - 1;
            return;
        }
        isMoreLeft = true;
        isMoreRight = true;
        listMin = number - 2;
        listMax = number + 2;
    }
}
