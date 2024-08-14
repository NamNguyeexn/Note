package com.example.initproj.Controllers;

import com.example.initproj.Models.User;
import com.example.initproj.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoginAPIController {
    @Autowired
    private UserService userService;
    @RequestMapping("/user")
    public ResponseEntity<User> checkLogin(@ModelAttribute("user") User user, Model model, HttpServletRequest request){
        try{
            user = userService.getUserByUsernameAndPassword(user.getEmail(), user.getPassword());
            if (user != null){
                model.addAttribute("user", user);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                System.out.println("LoginAPIController - found user");
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                System.out.println("LoginAPIController - not found");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw e;
        }
    }
    @RequestMapping(path = {"/login", "/logout"})
    public ModelAndView returnLogin (HttpSession session) {
        session.invalidate();
        return new ModelAndView("login", "user",new User());
    }
}
