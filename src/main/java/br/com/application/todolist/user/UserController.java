package br.com.application.todolist.user;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {

  private final IUserRepository userRepository;

  public UserController(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/")
  public ResponseEntity<?> create(@RequestBody UserModel user) {
    var userExisting = this.userRepository.findByUsername(user.getUsername());

    if (userExisting != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
          "error", "Bad Request",
          "message", "Username already exists"));
    }

    var hashedPassword = BCrypt.withDefaults()
        .hashToString(12, user.getPassword().toCharArray());

    user.setPassword(hashedPassword);

    var userCreated = this.userRepository.save(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
  }
}
