package com.batu.surveyapi.Data.Repositories;

import com.batu.surveyapi.Core.Entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
