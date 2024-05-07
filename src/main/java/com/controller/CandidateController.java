package com.controller;

import java.security.Principal;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.model.Candidate;
import com.model.User;
import com.service.CandidateService;
import com.service.UserService;

@Controller
public class CandidateController {
    
    @Autowired
    private CandidateService candidateService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/addcandidate") // vote
    public String addCandidate(@RequestParam("candidate") String candidateName,
                               Principal principal, Model model, HttpSession session) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);
        
        if (user.getStatus() == null) {
            try {
                // Get the selected candidate
                Candidate selectedCandidate = candidateService.getCandidateByCandidate(candidateName);
                if (selectedCandidate != null) {
                    // Update candidate votes
                    selectedCandidate.setVotes(selectedCandidate.getVotes() + 1);
                    candidateService.addCandidate(selectedCandidate);
                    
                    // Update user status
                    user.setStatus("Voted");
                    userService.addUser(user);
                    
                    session.setAttribute("vmsg", "Successfully Voted...");
                } else {
                    session.setAttribute("vmsg", "Candidate not found...");
                }
            } catch (Exception e) {
                session.setAttribute("vmsg", "Something went wrong...");
                e.printStackTrace();
            }
        } else {
            session.setAttribute("vmsg", "You have already voted...");
        }
        
        return "redirect:/user/dashboard";
    }
}
