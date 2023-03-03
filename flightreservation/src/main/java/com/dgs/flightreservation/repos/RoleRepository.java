package com.dgs.flightreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dgs.flightreservation.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
