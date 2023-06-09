package com.example.facebookapiserver.service.user;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.example.facebookapiserver.domain.common.Id;
import com.example.facebookapiserver.domain.user.Email;
import com.example.facebookapiserver.domain.user.User;
import com.example.facebookapiserver.event.UserJoinedEvent;
import com.example.facebookapiserver.exception.NotFoundException;
import com.example.facebookapiserver.controller.user.ConnectedUserDto;
import com.example.facebookapiserver.repository.user.ConnectionsRepository;
import com.example.facebookapiserver.repository.user.UserRepository;
import com.google.common.eventbus.EventBus;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ConnectionsRepository connectionsRepository;

    private final EventBus eventBus;


    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,ConnectionsRepository connectionsRepository,EventBus eventBus) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.connectionsRepository = connectionsRepository;
        this.eventBus = eventBus;
    }

    @Transactional
    public User join(String name,Email email,String password){
        checkArgument(isNotEmpty(password),"password must be provided");
        checkArgument(password.length() >=4 && password.length() <= 15, "password must be between 4 and 15 characters");

        User user = new User(name,email,password);
        User saved= insert(user);
        eventBus.post(new UserJoinedEvent(saved));
        return saved;
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Id<User,Long> userId){
        checkArgument(userId!=null, "userId must be provided");
        return userRepository.findById(userId.value());
    }


    @Transactional(readOnly = true)
    public Optional<User> findByEmail(Email email){
        checkArgument(email != null, "email must be provided");
        return userRepository.findByEmail(email);
    }


    public User insert(User user){
        return userRepository.save(user);
    }

    @Transactional
    public void updateProfileImage(Id<User, Long> userId, String profileImageUrl) {
        findById(userId).ifPresent(
            user -> user.updateProfileImage(profileImageUrl)
        );
    }

    @Transactional
    public User login(Email email, String password) {
        checkArgument(password != null, "password must be provided");
        User user = findByEmail(email).orElseThrow(() -> new NotFoundException(User.class, email));
        user.login(passwordEncoder,password);
        return user;
    }

    @Transactional(readOnly = true)
    public List<Id<User, Long>> findConnectedIds(Id<User, Long> userId) {
        checkArgument(userId != null , "id must be provided");
        List<Long> connectedIds = connectionsRepository.findConnectedIds(userId);
        return connectedIds.stream().map(connectedId-> Id.of(User.class,connectedId)).collect(toList());

    }

    @Transactional(readOnly = true)
    public List<ConnectedUserDto> findAllConnectedUser(Id<User, Long> userId) {
        checkArgument(userId != null , "id must be provided");
        return connectionsRepository.findAllConnectedUser(userId);
    }
}
