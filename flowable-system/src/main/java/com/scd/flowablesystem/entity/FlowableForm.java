package com.scd.flowablesystem.entity;

import com.sun.istack.internal.NotNull;
import lombok.Data;


@Data
public class FlowableForm {

    @NotNull
    private String formKey;

    @NotNull
    private String formName;

    private String formJson;
}
