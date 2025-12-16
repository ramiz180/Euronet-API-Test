package com.infinium.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseRequest {
    private String title;
    private String description;
    private int assignee;
    private List<Integer> watchers;
}
