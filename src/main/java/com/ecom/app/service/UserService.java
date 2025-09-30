package com.ecom.app.service;

import com.ecom.app.dto.AddressDTO;
import com.ecom.app.dto.UserRequest;
import com.ecom.app.dto.UserResponse;
import com.ecom.app.model.Address;
import com.ecom.app.repositories.UserRepository;
import com.ecom.app.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();
//    private long nextId=1L;

    public List<UserResponse> fetchAllUsers()
    {
//        return userList;
//        return userRepository.findAll();
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUsers( UserRequest userRequest)
    {
//        user.setId(nextId++);
//        userList.add(user);
//        return userList;

        User user= new User();
        updateUserFromRequst(user,userRequest);

        userRepository.save(user);
    }

    private void updateUserFromRequst(User user, UserRequest userRequest) {
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress()!=null)
        {
            Address address= new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }

    }

    public Optional<UserResponse> getUserById(Long id)
    {
//       for(User user:userList)
//       {
//           if(user.getId().equals(id))
//           {
//               return user;
//           }
//       }
//       return null;
            return userRepository.findById(id)
                    .map(this::mapToUserResponse);
    }

    public boolean updateUserById(Long id,UserRequest updateUserRequest)
    {
//        for(User user:userList)
//        {
//            if(user.getId().equals(id))
//            {
//                user.setFirstname(updatedUser.getFirstname());
//                user.setLastname(updatedUser.getLastname());
//                return user;
//            }
//        }
//        return null;
//        return userRepository.findById(id).map(
//                existingUser -> {
//                    existingUser.setFirstname(updatedUser.getFirstname());
//                    existingUser.setLastname(updatedUser.getLastname());
//                    userRepository.save(existingUser);
//                    return true;
//                }).orElse(false);

        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequst(existingUser,updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user)
    {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress()!=null)
        {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getStreet());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            addressDTO.setState(user.getAddress().getState());
            response.setAddress(addressDTO);
        }
        return response;
    }

    }
