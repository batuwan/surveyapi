package com.batu.surveyapi.Services;

import com.batu.surveyapi.Core.Entities.Option;
import com.batu.surveyapi.Core.Entities.Topic;
import com.batu.surveyapi.Data.Models.Survey.CreateTopicRequest;
import com.batu.surveyapi.Data.Models.Survey.UpdateTopicRequest;
import com.batu.surveyapi.Data.Repositories.OptionRepository;
import com.batu.surveyapi.Data.Repositories.TopicRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyService {

    TopicRepository topicRepository;
    OptionRepository optionRepository;

    public SurveyService(TopicRepository topicRepository, OptionRepository optionRepository) {
        this.topicRepository = topicRepository;
        this.optionRepository = optionRepository;
    }

    public Long createTopicAdmin(CreateTopicRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Topic topic = modelMapper.map(request, Topic.class);
        List<Option> optionList = new ArrayList<>();
        for (int i = 0; i < request.getOptions().size(); i++){
            Option option = modelMapper.map(request.getOptions().get(i),Option.class);
            option.setCount(0L);
            optionList.add(option);
        }
        topic.setOptions(optionList);
        topic.setApproved(true);
        topicRepository.save(topic);

        return topic.getId();
    }

    public Topic updateTopic(UpdateTopicRequest request, Long topicId) {

        Topic topic = topicRepository.findById(topicId).get();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(request,topic);
        topicRepository.save(topic);

        return topic;
    }

    public void deleteTopic(Long topicId) {
        topicRepository.deleteById(topicId);
    }

    public List<Topic> listWaitingTopics() {
        return topicRepository.findTopicsByIsApproved(false);
    }

    public void approveTopic(Long topicId) {
        Topic topic = topicRepository.findById(topicId).get();
        topic.setApproved(true);
        topicRepository.save(topic);
    }

    public List<Topic> listAvailableTopics() {
        return topicRepository.findTopicsByIsApproved(true);
    }

    public List<Option> listOptionsByTopicId(Long topicId) {
        return topicRepository.findById(topicId).get().getOptions();
    }

    public Topic sendAnswer(Long topicId, Long optionId) throws Exception {
        Option option = optionRepository.findById(optionId).get();
        if (!topicRepository.findById(topicId).get().isApproved()){throw new Exception();}
        option.setCount(option.getCount()+1);
        optionRepository.save(option);

        return topicRepository.findById(topicId).get();
    }

    public Topic getResults(Long topicId) {
        return topicRepository.findById(topicId).get();
    }

    public Long createTopic(CreateTopicRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Topic topic = modelMapper.map(request, Topic.class);
        List<Option> optionList = new ArrayList<>();
        for (int i = 0; i < request.getOptions().size(); i++){
            Option option = modelMapper.map(request.getOptions().get(i),Option.class);
            option.setCount(0L);
            optionList.add(option);
        }
        topic.setOptions(optionList);
        topic.setApproved(false);
        topicRepository.save(topic);

        return topic.getId();
    }
}
