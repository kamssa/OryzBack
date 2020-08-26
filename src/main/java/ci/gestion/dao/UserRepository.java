package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.gestion.entites.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
    List<User> findByIdIn(List<Long> userIds);
    Boolean existsByEmail(String email);
}
