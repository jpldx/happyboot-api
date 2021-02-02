package org.happykit.happyboot.op.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ImageDTO {
    private String uid;
    @NotEmpty(message = "url不能为空")
    @NotNull(message = "url不能为空")
    private String url;
    private String name;
    private String status;
}
