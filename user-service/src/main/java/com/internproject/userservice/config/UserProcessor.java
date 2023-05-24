package com.internproject.userservice.config;

import com.internproject.userservice.dto.request.UserCsv;
import com.internproject.userservice.entity.Role;
import com.internproject.userservice.entity.User;
import com.internproject.userservice.entity.UserDetail;
import com.internproject.userservice.repository.IRoleRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class UserProcessor implements ItemProcessor<UserCsv, User> {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public User process(UserCsv userCsv) throws Exception {
        User user = new User();
        UserDetail userDetail = new UserDetail();
        String username = userCsv.getFirstName().toLowerCase() + "." + userCsv.getLastName().toLowerCase();
        String password = bCryptPasswordEncoder.encode("123456");

        userDetail.setFullName(userCsv.getFirstName() + " " + userCsv.getLastName());
        userDetail.setGender(userCsv.getGender());
        userDetail.setPhone(userCsv.getPhone());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            java.util.Date utilDate = format.parse(userCsv.getDob());
            Date dob = new Date(utilDate.getTime());
            userDetail.setDob(dob);
        } catch (Exception e) {
            e.printStackTrace();
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(userCsv.getEmail());
        user.setUserDetail(userDetail);

        if (userCsv.getId().equals("1")) {
            List<Role> roles = roleRepository.findAll();
            user.setRoles(new HashSet<>(roles));
        } else {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
            user.setRoles(new HashSet<>(Arrays.asList(role.get())));
        }

        return user;
    }
}
