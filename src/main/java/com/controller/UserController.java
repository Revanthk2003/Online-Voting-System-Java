package com.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.model.User;
import com.model.Candidate;
import com.service.UserService;
import com.service.CandidateService;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CandidateService candidateService;
    
    @PostMapping("/user/vote")
    public String voteCandidate(@RequestParam("email") String email, @RequestParam("candidate") int candidateId, HttpSession session) {
        // Retrieve user by email
        User user = userService.getUserByEmail(email);
        if (user == null) {
            // Handle error if user not found
            session.setAttribute("vmsg", "User not found!");
            return "redirect:/user/dashboard";
        }
        
        // Retrieve candidate by ID
        Candidate candidate = candidateService.getCandidateById(candidateId);
        if (candidate == null) {
            // Handle error if candidate not found
            session.setAttribute("vmsg", "Candidate not found!");
            return "redirect:/user/dashboard";
        }
        
        // Update user status or perform other actions as needed
        // For example:
        user.setStatus("Voted");
        userService.updateUser(user);
        
        // Update candidate votes or perform other actions as needed
        // For example:
        candidate.setVotes(candidate.getVotes() + 1);
        candidateService.updateCandidate(candidate);
        
        session.setAttribute("vmsg", "Vote submitted successfully!");
        return "redirect:/user";
    }
    
    @GetMapping("/user")
    public String dashboard(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        
        model.addAttribute("user", user);
        model.addAttribute("title", "User Dashboard");
        
        List<Candidate> candidates = candidateService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        
        return "user/dashboard";
    }
    @PostMapping("/createuser")
    public String createUser(@ModelAttribute User user, HttpSession session) {
        String email = user.getEmail();
        if (userService.getUserByEmail(email) != null) {
            session.setAttribute("fail", "Registration Failed, Please try a different Email Id");
        } else {
            userService.addUser(user);
            session.setAttribute("msg", "Registration successful");
        }
        return "redirect:/register"; // Assuming /register is the registration page
    }

}
