package me.dio.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import me.dio.domain.model.User;
import me.dio.domain.model.dto.UserDTO;
import me.dio.domain.repository.UserRepository;
import me.dio.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
    	this.userRepository = userRepository;
    }
    
    @Transactional(readOnly = true)
	@Override
	public User findById(Long id) {
		
		return userRepository.findById(id)
				.orElseThrow(NoSuchElementException::new);
	}
    
    @Transactional(readOnly = true)
    @Override
    public List<User> FindAll() {
    	return userRepository.findAll();
    }

	@Transactional
	@Override
	public User create(User userToCreate) {
		if(userToCreate == null) {
			throw new IllegalArgumentException("The body is null");
		}
		else if(userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
			throw new IllegalArgumentException("This Account Number alrealdy exists");
		}
		
		
		return userRepository.save(userToCreate);
		
	}

	@Transactional
	@Override
	public void delete(Long id) {
		if(userRepository.existsById(id)) {
			userRepository.deleteById(id);
		}else {
			throw new IllegalArgumentException("This user ID doesn't exists");
		}
		
	}
	
	@Transactional
	@Override
	public User update(Long id, UserDTO userChanges) {
		User userData = this.findById(id);
		
		userChanges.getAccount().setId(userData.getAccount().getId());
		userChanges.getCard().setId(userData.getCard().getId());		
		userData.setAccount(userChanges.getAccount().toModel());
		userData.setCard(userChanges.getCard().toModel());
		userData.setName(userChanges.getName());

		
		return userRepository.save(userData);
	}
}