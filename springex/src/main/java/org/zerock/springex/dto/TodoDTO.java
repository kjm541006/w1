package org.zerock.springex.dto;

import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {

    private long tno;

    @NotEmpty
    private String title;

    @Future
    private LocalDate dueDate;

    private boolean completed;

    @NotEmpty
    private String writer;
}
