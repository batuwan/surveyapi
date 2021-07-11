package com.batu.surveyapi.Data.Repositories;

import com.batu.surveyapi.Core.Entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findTopicsByIsApproved(boolean isApproved);
}
