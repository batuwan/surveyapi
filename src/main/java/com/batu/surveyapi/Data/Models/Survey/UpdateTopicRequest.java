package com.batu.surveyapi.Data.Models.Survey;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateTopicRequest {

    private String description;
    private List<OptionCreateRequest> options = new ArrayList<>();
}
