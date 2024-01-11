package dev.practice.QRCodeGenerator.repository;

import dev.practice.QRCodeGenerator.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {

    // have to distinct uppercase and lowercase when writing query
    @Query("SELECT u FROM user u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    List<CustomUser> findByName(
            @Param(value = "firstName") String firstName,
            @Param(value = "lastName") String lastName);
}
