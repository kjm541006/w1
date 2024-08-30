package org.zerock.springex.dto;

import lombok.*;

import java.time.LocalDate;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private long tno;

    private String title;

    private LocalDate dueDate;

    private boolean completed;

    private String writer;
}
