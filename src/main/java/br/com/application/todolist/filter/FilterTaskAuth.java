package br.com.application.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.application.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  private IUserRepository userRepository;

  public FilterTaskAuth(IUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();

    return !path.startsWith("/tasks");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authorization = request.getHeader("Authorization");

    if (authorization == null || !authorization.startsWith("Basic ")) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"message\":\"Usuário não autorizado\"}");
      return;
    }

    String authEncoded = authorization.substring("Basic".length()).trim();

    byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
    String[] credentials = new String(authDecoded).split(":", 2);

    if (credentials.length < 2) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"message\":\"invalid credentials\"}");
      return;
    }

    String username = credentials[0];
    String password = credentials[1];

    var user = this.userRepository.findByUsername(username);

    if (user == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"message\":\"user not found\"}");
      return;
    }

    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

    if (!passwordVerify.verified) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"message\":\"invalid username or password\"}");
      return;
    }

    request.setAttribute("idUser", user.getId());
    filterChain.doFilter(request, response);
  }

}