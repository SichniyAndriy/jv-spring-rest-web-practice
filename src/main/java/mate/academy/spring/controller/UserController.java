package mate.academy.spring.controller;

import java.util.Optional;
import mate.academy.spring.mapper.DtoResponseMapper;
import mate.academy.spring.model.User;
import mate.academy.spring.model.dto.response.UserResponseDto;
import mate.academy.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DtoResponseMapper<UserResponseDto, User> userDtoResponseMapper;

    @Autowired
    public UserController(UserService userService,
                          DtoResponseMapper<UserResponseDto, User> userDtoResponseMapper) {
        this.userService = userService;
        this.userDtoResponseMapper = userDtoResponseMapper;
    }

    @GetMapping("/by-email?{email}")
    public UserResponseDto getByEmail(@PathVariable String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional.isPresent()
                ? userOptional.map(userDtoResponseMapper::toDto).get() : null;
    }
}