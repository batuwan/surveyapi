package com.batu.surveyapi.Data.Repositories;

import com.batu.surveyapi.Core.Entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
