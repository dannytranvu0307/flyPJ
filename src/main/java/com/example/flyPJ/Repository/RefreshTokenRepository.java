package com.example.flyPJ.Repository;

import java.util.Optional;

import com.example.flyPJ.Entity.RefreshToken;
import com.example.flyPJ.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;



@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Integer userId);

    @Modifying
    int deleteByUser(User user);
}
