package com.br.project.repository;

import com.br.project.model.NotificationsModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import java.util.UUID;
public interface NotificationsRepository extends ReactiveMongoRepository<NotificationsModel, UUID> {
}
