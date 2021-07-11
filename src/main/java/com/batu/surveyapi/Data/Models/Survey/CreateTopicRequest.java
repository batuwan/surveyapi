package com.batu.surveyapi.Data.Models.Survey;

import com.batu.surveyapi.Core.Entities.Option;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateTopicRequest {

    private String description;
    private List<OptionCreateRequest> options = new ArrayList<>();
}
