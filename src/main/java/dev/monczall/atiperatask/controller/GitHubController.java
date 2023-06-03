package dev.monczall.atiperatask.controller;

import dev.monczall.atiperatask.model.GitHubResponseDto;
import dev.monczall.atiperatask.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping(
            value = "/user/{userName}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<GitHubResponseDto> getUserRepos(@PathVariable String userName) {
        List<GitHubResponseDto> response = gitHubService.getUserReposAndBranches(userName);

        return response;
    }

}
