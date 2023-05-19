package com.br.repository;

import com.br.model.UserModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import java.util.UUID;

public interface UserRepository extends ReactiveMongoRepository<UserModel, UUID> {
}
