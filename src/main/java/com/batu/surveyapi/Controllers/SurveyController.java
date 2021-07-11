package com.batu.surveyapi.Controllers;

import com.batu.surveyapi.Core.Entities.Option;
import com.batu.surveyapi.Core.Entities.Topic;
import com.batu.surveyapi.Data.Models.Survey.CreateTopicRequest;
import com.batu.surveyapi.Data.Models.Survey.UpdateTopicRequest;
import com.batu.surveyapi.Services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    //region Admin Endpoints
    @PostMapping("/admin/createTopic")
    public ResponseEntity<?> createTopicAdmin(@RequestBody CreateTopicRequest request){
        return ResponseEntity.ok(surveyService.createTopicAdmin(request));
    }

    @PutMapping("/admin/updateTopic/{topicId}")
    public ResponseEntity<?> updateTopicAdmin(@PathVariable Long topicId, @RequestBody UpdateTopicRequest request){

        var topic = surveyService.updateTopic(request, topicId);
        return ResponseEntity.ok().body(topic);
    }

    @DeleteMapping("/admin/deleteTopic/{topicId}")
    public ResponseEntity<?> deleteTopicAdmin(@PathVariable Long topicId){
        surveyService.deleteTopic(topicId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/admin/listWaitingTopics")
    public ResponseEntity<?> listWaitingTopics(){
        return ResponseEntity.ok(surveyService.listWaitingTopics());
    }

    @PatchMapping("/admin/approveTopic/{topicId}")
    public ResponseEntity<?> approveTopic(@PathVariable Long topicId){
        surveyService.approveTopic(topicId);
        return ResponseEntity.ok(true);
    }
    //endregion

    //region Common Endpoints
    @GetMapping("/getResults/{topicId}")
    public ResponseEntity<?> getResults(@PathVariable Long topicId){
        return ResponseEntity.ok(surveyService.getResults(topicId));
    }

    @GetMapping("/listAvailableTopics")
    public ResponseEntity<?> listAvailableTopics(){
        return ResponseEntity.ok(surveyService.listAvailableTopics());
    }

    @GetMapping("/listOptions/{topicId}")
    public ResponseEntity<?> listOptionsByTopicId(@PathVariable Long topicId){
        return ResponseEntity.ok(surveyService.listOptionsByTopicId(topicId));
    }

    @PutMapping("/{topicId}/sendAnswer/{optionId}")
    public ResponseEntity<?> sendAnswer(@PathVariable Long topicId, @PathVariable Long optionId){
        Topic topic = surveyService.sendAnswer(topicId, optionId);
        return ResponseEntity.ok(topic);
    }

    @PostMapping("/createTopic")
    public ResponseEntity<?> createTopic(@RequestBody CreateTopicRequest request){
        Long createdTopicId = surveyService.createTopic(request);
        return ResponseEntity.ok(createdTopicId);
    }


    //endregion
}
