package me.dio.service.impl;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
    	this.userRepository = userRepository;
    }
    
	@Override
	public User findbyId(Long id) {
		
		return userRepository.findById(id)
				.orElseThrow(NoSuchElementException::new);
	}

	@Override
	public User create(User userToCreate) {
		if(userToCreate == null) {
			throw new IllegalArgumentException("This user ID alrealdy exists");
		}
		else if(userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
			throw new IllegalArgumentException("This Account Number alrealdy exists");
		}
		
		
		return userRepository.save(userToCreate);
		
	}

}