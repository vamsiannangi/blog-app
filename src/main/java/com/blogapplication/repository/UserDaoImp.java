//package com.blogapplication.repository;
//
//import jakarta.persistence.EntityManager;
//import org.apache.catalina.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//
//    @Repository
//    public class UserDaoImp implements UserDao {
//        private EntityManager entityManager;
//
//        // inject entity manager using constructor injection
//        @Autowired
//        public UserDaoImp(EntityManager entityManager) {
//            this.entityManager = entityManager;
//        }
//
//        @Override
//        public User getUserById(Long id) {
//            return entityManager.find(User.class, id);
//        }
//    }
//
