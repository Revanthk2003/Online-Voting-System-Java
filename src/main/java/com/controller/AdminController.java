package com.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Candidate;
import com.service.CandidateService;

@Controller
public class AdminController {
    
    @Autowired
    private CandidateService candidateService;
    
    @GetMapping("/admin")
    public String dashboard(Model model, Principal principal) {
        List<Candidate> candidates = candidateService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        model.addAttribute("title", "Admin Dashboard");
        return "admin/dashboard";
    }
    
    @PostMapping("/admin/addCandidate")
    public String addCandidate(@RequestParam("candidateName") String candidateName,
                               @RequestParam("candidateVotes") int candidateVotes) {
        Candidate candidate = new Candidate(candidateName, candidateVotes);
        candidateService.addCandidate(candidate);
        return "redirect:/admin";
    }
    
    @PostMapping("/admin/removeCandidate")
    public String removeCandidate(@RequestParam("id") int candidateId) {
        candidateService.removeCandidate(candidateId);
        return "redirect:/admin";
    }
}
