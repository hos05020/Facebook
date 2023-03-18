package com.example.facebook.user;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import com.example.facebook.common.Id;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User join(String name,Email email,String password){
        checkArgument(isNotEmpty(password),"password must be provided");
        checkArgument(password.length() >=4 && password.length() <= 15, "password must be between 4 and 15 characters");

        User user = new User(name,email,password);
        return insert(user);
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
}
