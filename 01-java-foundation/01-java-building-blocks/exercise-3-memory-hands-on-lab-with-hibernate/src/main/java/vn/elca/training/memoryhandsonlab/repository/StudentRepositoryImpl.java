package vn.elca.training.memoryhandsonlab.repository;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StudentRepositoryImpl implements StudentCustomRepository {
    @PersistenceContext
    private EntityManager em;
}
