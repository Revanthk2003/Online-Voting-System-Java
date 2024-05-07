package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Candidate;
import com.repository.CandidateRepository;

@Service
public class CandidateService {
    
    @Autowired
    private CandidateRepository candidateRepository;
    
    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
    
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
    
    public Candidate getCandidateById(int id) {
        return candidateRepository.getById(id);
    }
    
    public void removeCandidate(int id) {
        candidateRepository.deleteById(id);
    }
    
    public int getNumOfVotes(String candidate) {
        return candidateRepository.getNumOfVotes(candidate);
    }

    public Candidate getCandidateByCandidate(String candidate) {
        return candidateRepository.getCandidateByCandidate(candidate);
    }
    
    public Candidate updateCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
