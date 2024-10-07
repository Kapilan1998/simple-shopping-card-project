package com.testShoopingCard.shopping_card.Repository;

import com.testShoopingCard.shopping_card.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {

    Optional<Role> findByName(String role);
}
