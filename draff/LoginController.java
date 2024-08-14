package com.example.initproj.Controllers;

import com.example.initproj.Models.User;
import com.example.initproj.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
    public String checkLogin(@ModelAttribute("user") User user, Model model, HttpServletRequest request){
        try{
            user = userService.getUserByUsernameAndPassword(user.getEmail(), user.getPassword());
            if (user != null){
                model.addAttribute("user", user);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                System.out.println("-----------------------------");
                System.out.println("LoginController - found user");
                return "user";
            } else {
                model.addAttribute("loi", "Ten dang nhap hoac mat khau chua dung, vui long nhap lai!");
                System.out.println("-----------------------------");
                System.out.println("LoginController - not found");
                return "login";
            }
        } catch (Exception e) {
            throw e;
        }
    }
    @RequestMapping(path = {"/logout" ,"/login"})
    public ModelAndView returnLogin (HttpSession session) {
        session.invalidate();
        return new ModelAndView("login", "user",new User());
    }
}
