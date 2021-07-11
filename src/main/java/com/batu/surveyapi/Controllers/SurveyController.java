package com.batu.surveyapi.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    //region Admin Endpoints
    @PostMapping("/admin/createTopic")
    public ResponseEntity<?> createTopicAdmin(){
        return ResponseEntity.ok(true);
    }

    @PutMapping("/admin/updateTopic/{topicId}")
    public ResponseEntity<?> updateTopicAdmin(){
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/admin/deleteTopic/{topicId}")
    public ResponseEntity<?> deleteTopicAdmin(){
        return ResponseEntity.ok(true);
    }
    //endregion

    //region Common Endpoints
    @GetMapping("/getResults/{topicId}")
    public ResponseEntity<?> getResults(@PathVariable Long topicId){
        return ResponseEntity.ok(true);
    }

    @GetMapping("/listAvailableTopics")
    public ResponseEntity<?> listAvailableTopics(){
        return ResponseEntity.ok(true);
    }

    @GetMapping("/listOptions/{topicId}")
    public ResponseEntity<?> listOptionsByTopicId(){
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{topicId}/sendAnswer/{optionId}")
    public ResponseEntity<?> sendAnswer(){
        return ResponseEntity.ok(true);
    }

    @PostMapping("/createTopic")
    public ResponseEntity<?> createTopic(){
        return ResponseEntity.ok(true);
    }


    //endregion
}
